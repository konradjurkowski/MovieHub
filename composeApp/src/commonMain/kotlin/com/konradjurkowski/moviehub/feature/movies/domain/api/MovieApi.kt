package com.konradjurkowski.moviehub.feature.movies.domain.api

import io.ktor.client.statement.HttpResponse

interface MovieApi {
    suspend fun getMovieById(movieId: Long): HttpResponse
    suspend fun searchMovies(query: String, page: Int = 1): HttpResponse
    suspend fun getPopularMovies(page: Int = 1): HttpResponse
}
