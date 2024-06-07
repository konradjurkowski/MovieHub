package feature.movies.data.repository

import core.utils.Resource
import feature.movies.data.api.dto.Genre
import feature.movies.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): Resource<List<Movie>>
    suspend fun getTopRatedMovies(): Resource<List<Movie>>
    suspend fun getGenres(): Resource<List<Genre>>
}
