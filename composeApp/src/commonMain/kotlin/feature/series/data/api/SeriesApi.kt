package feature.series.data.api

import io.ktor.client.statement.HttpResponse

interface SeriesApi {
    suspend fun getSeriesById(seriesId: Long): HttpResponse
    suspend fun searchSeries(query: String, page: Int = 1): HttpResponse
    suspend fun getCredits(seriesId: Long): HttpResponse
    suspend fun getPopularSeries(page: Int = 1): HttpResponse
}
