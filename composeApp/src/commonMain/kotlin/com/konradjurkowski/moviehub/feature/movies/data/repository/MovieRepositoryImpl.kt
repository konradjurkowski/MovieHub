package com.konradjurkowski.moviehub.feature.movies.data.repository

import com.konradjurkowski.moviehub.core.utils.helpers.safeApiCall
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.MovieDetailsDto
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.MovieDto
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.request.AddMovieRequest
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.response.AddedTmdbIdsResponse
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.toDomain
import com.konradjurkowski.moviehub.feature.movies.domain.api.MovieApi
import com.konradjurkowski.moviehub.feature.movies.domain.repository.MovieRepository
import com.konradjurkowski.moviehub.feature.movies.domain.storage.MovieStorage
import io.ktor.client.call.body

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val storage: MovieStorage,
) : MovieRepository {

    override suspend fun addMovie(request: AddMovieRequest) =
        safeApiCall(apiCall = { api.addMovie(request) }) { response ->
            response.body<MovieDto>().toDomain()
        }

    override suspend fun getAddedTmdbIds(groupId: Long) =
        safeApiCall(apiCall = { api.getAddedTmdbIds(groupId) }) { response ->
            response.body<AddedTmdbIdsResponse>().movies.also { movieIds ->
                storage.saveTmdbIds(movieIds)
            }
        }

    override suspend fun getMoviePreview(tmdbId: Long) =
        safeApiCall(apiCall = { api.getMoviePreview(tmdbId) }) { response ->
            response.body<MovieDetailsDto>().toDomain()
        }
}
