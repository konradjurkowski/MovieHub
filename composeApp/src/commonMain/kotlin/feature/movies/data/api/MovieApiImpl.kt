package feature.movies.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.path

class MovieApiImpl(
    private val httpClient: HttpClient
) : MovieApi {
    override suspend fun getMovieById(movieId: Long): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/movie/$movieId") }
        }

    override suspend fun searchMovies(query: String, page: Int): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url {
                path("/3/search/movie")
                parameters.apply {
                    append("query", query)
                    append("page", page.toString())
                }
            }
        }

    override suspend fun getCredits(movieId: Long): HttpResponse =
        httpClient.request {
            method = HttpMethod.Get
            url { path("/3/movie/$movieId/credits") }
        }
}
