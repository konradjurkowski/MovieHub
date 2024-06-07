package feature.series.data.repository

import core.utils.Resource
import core.utils.safeApiCall
import feature.movies.data.api.dto.Genre
import feature.movies.data.api.dto.GenresResponse
import feature.series.domain.model.Series
import feature.series.domain.model.toSeries
import feature.series.data.api.SeriesApi
import feature.series.data.api.dto.SeriesResponse
import io.ktor.client.call.body

class SeriesRepositoryImpl(
    private val seriesApi: SeriesApi
) : SeriesRepository {
    override suspend fun getPopularSeries(): Resource<List<Series>> =
        safeApiCall(call = { seriesApi.getPopularSeries() }) { response ->
            val series = response.body<SeriesResponse>().results.map { it.toSeries() }
            Resource.Success(series)
        }

    override suspend fun getTopRatedSeries(): Resource<List<Series>> =
        safeApiCall(call = { seriesApi.getTopRatedSeries() }) { response ->
            val series = response.body<SeriesResponse>().results.map { it.toSeries() }
            Resource.Success(series)
        }

    override suspend fun getGenres(): Resource<List<Genre>> =
        safeApiCall(call = { seriesApi.getGenres() }) { response ->
            Resource.Success(response.body<GenresResponse>().genres)
        }
}
