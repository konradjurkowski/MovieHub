package com.konradjurkowski.moviehub.feature.movies.presentation.movies

import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.feature.movies.domain.model.Movie

@MviIntent
sealed class MoviesIntent {
    data object AddMovieClick : MoviesIntent()
    data class MovieClick(val movie: Movie) : MoviesIntent()
    data object Refresh : MoviesIntent()
}

@MviEvent
sealed class MoviesEvent {

}

@MviState
sealed class MoviesState {
    data object Idle : MoviesState()
    data object Loading : MoviesState()
    data class Error(val error: Throwable? = null) : MoviesState()
    data class Success(val movies: List<Movie>) : MoviesState()

    val isIdle get() = this is Idle
}
