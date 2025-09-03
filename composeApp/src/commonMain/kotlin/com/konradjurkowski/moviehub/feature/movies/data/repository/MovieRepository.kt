package com.konradjurkowski.moviehub.feature.movies.data.repository

import com.konradjurkowski.moviehub.core.utils.helpers.safeApiCall
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.MovieDetailsDto
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.toDomain
import com.konradjurkowski.moviehub.feature.movies.domain.api.MovieApi
import com.konradjurkowski.moviehub.feature.movies.domain.repository.MovieRepository
import io.ktor.client.call.body

class MovieRepositoryImpl(
    private val api: MovieApi,
) : MovieRepository {

    override suspend fun getMovieById(movieId: Long) =
        safeApiCall(apiCall = { api.getMovieById(movieId) }) { response ->
            response.body<MovieDetailsDto>().toDomain()
        }
}
