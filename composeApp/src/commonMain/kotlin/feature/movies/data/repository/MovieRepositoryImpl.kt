package feature.movies.data.repository

import core.utils.Resource
import core.utils.safeApiCall
import feature.movies.data.api.MovieApi
import feature.movies.data.api.dto.Genre
import feature.movies.data.api.dto.GenresResponse
import feature.movies.data.api.dto.MoviesResponse
import feature.movies.domain.model.Movie
import feature.movies.domain.model.toMovie
import io.ktor.client.call.body

class MovieRepositoryImpl(
    private val movieApi: MovieApi
) : MovieRepository {
    override suspend fun getPopularMovies(): Resource<List<Movie>> =
        safeApiCall(call = { movieApi.getPopularMovies() }) { response ->
            val movies = response.body<MoviesResponse>().results.map { it.toMovie() }
            Resource.Success(movies)
        }

    override suspend fun getTopRatedMovies(): Resource<List<Movie>> =
        safeApiCall(call = { movieApi.getTopRatedMovies() }) { response ->
            val movies = response.body<MoviesResponse>().results.map { it.toMovie() }
            Resource.Success(movies)
        }

    override suspend fun getGenres(): Resource<List<Genre>> =
        safeApiCall(call = { movieApi.getGenres() }) { response ->
            Resource.Success(response.body<GenresResponse>().genres)
        }
}
