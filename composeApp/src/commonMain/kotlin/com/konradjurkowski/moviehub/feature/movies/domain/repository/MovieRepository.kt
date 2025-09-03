package com.konradjurkowski.moviehub.feature.movies.domain.repository

import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails

interface MovieRepository {
    suspend fun getMovieById(movieId: Long) : Response<MovieDetails>
}
