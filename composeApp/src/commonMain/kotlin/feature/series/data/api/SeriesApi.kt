package feature.series.data.api

import io.ktor.client.statement.HttpResponse

interface SeriesApi {
    suspend fun getPopularSeries(): HttpResponse
    suspend fun getTopRatedSeries(): HttpResponse
    suspend fun getGenres(): HttpResponse
}
