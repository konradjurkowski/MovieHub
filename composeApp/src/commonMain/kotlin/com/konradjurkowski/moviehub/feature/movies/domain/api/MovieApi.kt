package com.konradjurkowski.moviehub.feature.movies.domain.api

import com.konradjurkowski.moviehub.feature.movies.data.api.dto.request.AddMovieRequest
import io.ktor.client.statement.HttpResponse

interface MovieApi {
    suspend fun addMovie(request: AddMovieRequest): HttpResponse
    suspend fun getAddedTmdbIds(groupId: Long): HttpResponse
    suspend fun getMovieLeaderboard(groupId: Long, page: Int = 1): HttpResponse
    suspend fun searchMovies(query: String, page: Int = 1): HttpResponse
    suspend fun getPopularMovies(page: Int = 1): HttpResponse
    suspend fun getMoviePreview(tmdbId: Long): HttpResponse
}
