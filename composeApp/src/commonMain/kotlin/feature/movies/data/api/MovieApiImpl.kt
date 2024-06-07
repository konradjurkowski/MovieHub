package feature.movies.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.path

class MovieApiImpl(
    private val httpClient: HttpClient
) : MovieApi {
    override suspend fun getPopularMovies(): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/movie/popular") }
        }

    override suspend fun getTopRatedMovies(): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/movie/top_rated") }
        }

    override suspend fun getGenres(): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/genre/movie/list") }
        }
}
