package feature.series.data.repository

import core.utils.Resource
import feature.movies.data.api.dto.Genre
import feature.series.domain.model.Series

interface SeriesRepository {
    suspend fun getPopularSeries(): Resource<List<Series>>
    suspend fun getTopRatedSeries(): Resource<List<Series>>
    suspend fun getGenres(): Resource<List<Genre>>
}
