package feature.movies.data.api

import io.ktor.client.statement.HttpResponse

interface MovieApi {
    suspend fun getPopularMovies(): HttpResponse
    suspend fun getTopRatedMovies(): HttpResponse
    suspend fun getGenres(): HttpResponse
    suspend fun searchMovies(query: String, page: Int = 1): HttpResponse
}
