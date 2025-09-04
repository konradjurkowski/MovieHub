package com.konradjurkowski.moviehub.feature.movies.domain.api

import com.konradjurkowski.moviehub.feature.movies.data.api.dto.request.CreateMovieRequest
import io.ktor.client.statement.HttpResponse

interface MovieApi {
    suspend fun createMovie(request: CreateMovieRequest): HttpResponse
    suspend fun getAddedTmdbIds(groupId: Long): HttpResponse
    suspend fun searchMovies(query: String, page: Int = 1): HttpResponse
    suspend fun getPopularMovies(page: Int = 1): HttpResponse
    suspend fun getMoviePreview(tmdbId: Long): HttpResponse
}
