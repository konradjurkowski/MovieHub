package com.konradjurkowski.moviehub.feature.movies.data.api

import com.konradjurkowski.moviehub.feature.movies.data.api.dto.request.AddMovieRequest
import com.konradjurkowski.moviehub.feature.movies.domain.api.MovieApi
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class MovieApiImpl(
    private val httpClient: HttpClient,
) : MovieApi {

    override suspend fun addMovie(request: AddMovieRequest) = httpClient.request {
        method = HttpMethod.Post
        url { path("/api/movies/add") }
        setBody(request)
    }

    override suspend fun getAddedTmdbIds(groupId: Long) = httpClient.request {
        method = HttpMethod.Get
        url {
            path("/api/movies/ids")
            parameters.append("groupId", groupId.toString())
        }
    }

    override suspend fun searchMovies(query: String, page: Int) = httpClient.request {
        method = HttpMethod.Get
        url {
            path("/api/movies/search")
            parameters.append("query", query)
            parameters.append("page", page.toString())
        }
    }

    override suspend fun getPopularMovies(page: Int) = httpClient.request {
        method = HttpMethod.Get
        url {
            path("/api/movies/popular")
            parameters.append("page", page.toString())
        }
    }

    override suspend fun getMoviePreview(tmdbId: Long) = httpClient.request {
        method = HttpMethod.Get
        url { path("/api/movies/preview/$tmdbId") }
    }
}
