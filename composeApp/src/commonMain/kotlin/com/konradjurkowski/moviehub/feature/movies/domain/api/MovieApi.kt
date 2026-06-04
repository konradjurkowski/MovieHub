package com.konradjurkowski.moviehub.feature.movies.domain.api

import com.konradjurkowski.moviehub.feature.movies.data.api.dto.request.AddMovieRequest
import io.ktor.client.statement.HttpResponse

interface MovieApi {
    suspend fun addMovie(request: AddMovieRequest): HttpResponse
    suspend fun getAddedTmdbIds(): HttpResponse
    suspend fun getMovieLeaderboard(page: Int = 1): HttpResponse
    suspend fun searchMovies(query: String, page: Int = 1): HttpResponse
    suspend fun getPopularMovies(page: Int = 1): HttpResponse
    suspend fun getMoviePreview(tmdbId: Long): HttpResponse
}
