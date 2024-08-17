package core.di

import com.plusmobileapps.konnectivity.Konnectivity
import core.tools.logger.KtorLogger
import core.utils.Constants
import core.utils.getPlatform
import core.utils.isDebug
import core.utils.languageCode
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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

    single<HttpClient> {
        HttpClient {
            if (getPlatform().isDebug) {
                install(Logging) {
                    logger = KtorLogger()
                    level = LogLevel.ALL
                }
            }
            install(ContentNegotiation) {
                json(get())
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = Constants.BASE_URL
                    parameters.append("language", getPlatform().languageCode)
                }
                header("Authorization", "Bearer ${Constants.API_KEY}")
            }
        }
    }
}
