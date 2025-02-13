package core.di

import com.plusmobileapps.konnectivity.Konnectivity
import core.tools.logger.KtorLogger
import core.utils.constants.Constants
import core.utils.constants.MovieApiConstants
import core.utils.PlatformInfo
import core.utils.constants.FirebaseMessagingConstants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single<Json> {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    single { Konnectivity() }

    single<HttpClient>(named(MovieApiConstants.NAME)) {
        HttpClient {
            if (PlatformInfo.isDebug) {
                install(Logging) {
                    logger = KtorLogger()
                    level = LogLevel.ALL
                }
            }
            install(ContentNegotiation) {
                json(get())
            }
            install(HttpTimeout) {
                requestTimeoutMillis = Constants.REQUEST_TIMEOUT_IN_MS
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = MovieApiConstants.BASE_URL
                    parameters.append(Constants.LANGUAGE_KEY, PlatformInfo.getLanguageCode())
                }
                header(Constants.AUTHORIZATION_KEY, "${Constants.BEARER_KEY} ${MovieApiConstants.API_KEY}")
            }
        }
    }

    single<HttpClient>(named(FirebaseMessagingConstants.NAME)) {
        HttpClient {
            if (PlatformInfo.isDebug) {
                install(Logging) {
                    logger = KtorLogger()
                    level = LogLevel.ALL
                }
            }
            install(ContentNegotiation) {
                json(get())
            }
            install(HttpTimeout) {
                requestTimeoutMillis = Constants.REQUEST_TIMEOUT_IN_MS
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTPS
                    host = FirebaseMessagingConstants.BASE_URL
                }
                header(Constants.AUTHORIZATION_KEY, "${Constants.BEARER_KEY} ${FirebaseMessagingConstants.API_KEY}")
            }
        }
    }
}
