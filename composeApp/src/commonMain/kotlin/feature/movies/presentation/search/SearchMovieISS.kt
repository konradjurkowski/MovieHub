package feature.movies.presentation.search

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.movies.domain.model.Movie

@MviIntent
sealed class SearchMovieIntent {
    data class QueryChanged(val query: String) : SearchMovieIntent()
    data object ClearQueryPressed : SearchMovieIntent()
    data class MovieAddPressed(val movie: Movie) : SearchMovieIntent()
    data class MovieCardPressed(val movie: Movie) : SearchMovieIntent()
}

@MviSideEffect
sealed class SearchMovieSideEffect {
    data object ShowLoader : SearchMovieSideEffect()
    data class GoToMoviePreview(val movie: Movie) : SearchMovieSideEffect()
    data object HideLoaderWithSuccess : SearchMovieSideEffect()
    data class HideLoaderWithError(val error: Throwable) : SearchMovieSideEffect()
}

@MviState
data class SearchMovieState(
    val query: String = "",
    val isSearchInitiated: Boolean = false,
    val addedMovieIds: List<Long> = emptyList(),
)