package com.konradjurkowski.moviehub.core.data.api

import com.konradjurkowski.moviehub.core.domain.api.ApiClientFactory
import com.konradjurkowski.moviehub.core.utils.PlatformInfo
import com.konradjurkowski.moviehub.core.utils.constants.ApiConstants
import com.konradjurkowski.moviehub.core.utils.logger.KtorLogger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClientFactoryImpl : ApiClientFactory {

    override fun createClient(baseUrl: String): HttpClient {
        val json = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }

        return HttpClient {
            if (PlatformInfo.isDebug) {
                install(Logging) {
                    logger = KtorLogger()
                    level = LogLevel.ALL
                }
            }
            install(ContentNegotiation) {
                json(json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = ApiConstants.REQUEST_TIMEOUT_IN_MS
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(baseUrl)
            }
        }
    }
}
