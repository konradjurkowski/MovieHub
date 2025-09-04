package com.konradjurkowski.moviehub.feature.movies.domain.repository

import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.request.AddMovieRequest
import com.konradjurkowski.moviehub.feature.movies.domain.model.Movie
import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails

interface MovieRepository {
    suspend fun addMovie(request: AddMovieRequest): Response<Movie>
    suspend fun getAddedTmdbIds(groupId: Long): Response<List<Long>>
    suspend fun getMoviePreview(tmdbId: Long): Response<MovieDetails>
}
