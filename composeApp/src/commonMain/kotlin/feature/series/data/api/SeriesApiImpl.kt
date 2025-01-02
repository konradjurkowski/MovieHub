package feature.series.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.path

class SeriesApiImpl(
    private val httpClient: HttpClient
) : SeriesApi {

    override suspend fun getSeriesById(seriesId: Long): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/tv/$seriesId") }
        }

    override suspend fun searchSeries(query: String, page: Int): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url {
                path("/3/search/tv")
                parameters.apply {
                    append("query", query)
                    append("page", page.toString())
                }
            }
        }

    override suspend fun getCredits(seriesId: Long): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/tv/$seriesId/aggregate_credits") }
        }

    override suspend fun getPopularSeries(page: Int): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url {
                path("/3/tv/popular")
                parameters.apply {
                    append("page", page.toString())
                }
            }
        }
}
