package com.konradjurkowski.moviehub.feature.movies.presentation.add.ise

import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.feature.movies.domain.model.Movie

@MviIntent
sealed class AddMovieIntent {
    data object ClearQueryPressed : AddMovieIntent()
    data class MovieAddPressed(val movie: Movie) : AddMovieIntent()
    data class MovieCardPressed(val movie: Movie) : AddMovieIntent()
    data class QueryChanged(val query: String) : AddMovieIntent()
}

@MviEvent
sealed class AddMovieEvent {

}

@MviState
data class AddMovieState(
    val query: String = "",
    val addedMovieIds: List<Long> = emptyList(),
)
