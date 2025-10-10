package com.konradjurkowski.moviehub.feature.auth.data.repository

import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.utils.helpers.safeApiCall
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.LoginResponse
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.RegisterResponse
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.UserDetailsResponse
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.toDomain
import com.konradjurkowski.moviehub.feature.auth.domain.api.AuthApi
import com.konradjurkowski.moviehub.feature.auth.domain.api.UserApi
import com.konradjurkowski.moviehub.feature.auth.domain.model.User
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.domain.storage.AuthDataStore
import io.ktor.client.call.body
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val userApi: UserApi,
    private val authDataStore: AuthDataStore,
    private val dispatchersProvider: DispatchersProvider,
) : AuthRepository {

    private val scope = CoroutineScope(SupervisorJob() + dispatchersProvider.main)

    override val userFlow = authDataStore.userFlow

    override suspend fun getUser() = authDataStore.getUser()

    override suspend fun isUserLoggedIn() =
        authDataStore.getAccessToken() != null && authDataStore.getRefreshToken() != null && authDataStore.getUser() != null

    override suspend fun login(email: String, password: String) =
        safeApiCall(apiCall = { authApi.login(email, password) }) { response ->
            val (userDto, accessToken, refreshToken) = response.body<LoginResponse>()
            authDataStore.saveUser(userDto.toDomain())
            authDataStore.saveAccessToken(accessToken)
            authDataStore.saveRefreshToken(refreshToken)
            userDto.toDomain()
        }

    override suspend fun register(name: String, email: String, password: String) =
        safeApiCall(apiCall = { authApi.register(name = name, email = email, password = password) }) { response ->
            response.body<RegisterResponse>()
        }

    override suspend fun getUserDetails(): Response<User> =
        safeApiCall(apiCall = { userApi.getUserDetails() }) { response ->
            response.body<UserDetailsResponse>().user
                .toDomain()
                .also { authDataStore.saveUser(user = it) }
        }

    override suspend fun clearUserSession() {
        authDataStore.clear()
    }

    override fun logout() {
        scope.launch(dispatchersProvider.io) {
            val refreshToken = authDataStore.getRefreshToken() ?: return@launch
            safeApiCall(apiCall = { authApi.logout(refreshToken = refreshToken) }) { /* NO - OP */ }
        }
    }
}
