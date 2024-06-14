package feature.add.presentation.search_movie

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState

@MviIntent
sealed class SearchMovieIntent {
    data class QueryChanged(val query: String) : SearchMovieIntent()
    data class SearchPressed(val query: String) : SearchMovieIntent()
}

@MviSideEffect
sealed class SearchMovieSideEffect {
}

@MviState
data class SearchMovieState(
    val query: String = "",
    val isSearchInitiated: Boolean = false,
)
