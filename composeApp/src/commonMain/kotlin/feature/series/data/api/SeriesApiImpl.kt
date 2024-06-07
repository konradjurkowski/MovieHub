package feature.series.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.path

class SeriesApiImpl(
    private val httpClient: HttpClient
) : SeriesApi {
    override suspend fun getPopularSeries(): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/tv/popular") }
        }

    override suspend fun getTopRatedSeries(): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/tv/top_rated") }
        }

    override suspend fun getGenres(): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/genre/tv/list") }
        }
}
