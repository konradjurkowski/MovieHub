package com.konradjurkowski.moviehub.core.data.api

import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.request.RefreshTokenRequest
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.RefreshTokenResponse
import com.konradjurkowski.moviehub.feature.auth.domain.storage.AuthDataStore
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.encodedPath
import io.ktor.util.AttributeKey
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AuthInterceptor(
    private val authDataStore: AuthDataStore,
    private val httpClient: HttpClient,
    private val navigator: AppNavigator,
) : HttpClientPlugin<Unit, Unit> {

    private val mutex = Mutex()

    private val publicEndpoints = listOf(
        "/api/auth/login",
        "/api/auth/register",
        "/api/auth/refresh",
    )

    override val key = AttributeKey<Unit>("AuthInterceptor")

    override fun prepare(block: Unit.() -> Unit) = Unit

    override fun install(plugin: Unit, scope: HttpClient) {
        scope.plugin(HttpSend.Plugin).intercept { request ->
            val requestUrl = request.url.encodedPath
            val isPublicEndpoint = publicEndpoints.any { endpoint ->
                requestUrl.endsWith(endpoint)
            }
            if (isPublicEndpoint) return@intercept execute(request)

            val accessToken = authDataStore.getAccessToken() ?: ""
            request.headers.append(HttpHeaders.Authorization, "Bearer $accessToken")
            val call = execute(request)

            if (call.response.status == HttpStatusCode.Companion.Unauthorized) {
                val newAccessToken = mutex.withLock {
                    val currentToken = authDataStore.getAccessToken()
                    if (currentToken != accessToken) return@withLock currentToken

                    refreshToken()
                }
                if (newAccessToken == null) {
                    authDataStore.clear()
                    navigator.replaceAll(AuthDestination.WelcomeRoute)
                    return@intercept call
                }

                request.headers.remove(HttpHeaders.Authorization)
                request.headers.append(HttpHeaders.Authorization, "Bearer $newAccessToken")
                return@intercept execute(request)
            }

            return@intercept call
        }
    }

    private suspend fun refreshToken(): String? {
        return runCatching {
            val refreshToken = authDataStore.getRefreshToken() ?: return null
            val response = httpClient.post("/api/auth/refresh") {
                setBody(RefreshTokenRequest(refreshToken))
            }
            val responseBody = response.body<RefreshTokenResponse>()
            authDataStore.saveAccessToken(responseBody.accessToken)
            authDataStore.saveRefreshToken(responseBody.refreshToken)
            responseBody.accessToken
        }.getOrNull()
    }
}
