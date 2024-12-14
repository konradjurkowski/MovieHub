package feature.movies.presentation.preview

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.movies.domain.model.CastData
import feature.movies.domain.model.MovieDetails

@MviIntent
sealed class MoviePreviewIntent {
    data object BackPressed : MoviePreviewIntent()
    data object Refresh : MoviePreviewIntent()
    data class MovieAddPressed(val movie: MovieDetails) : MoviePreviewIntent()
}

@MviSideEffect
sealed class MoviePreviewSideEffect {
    data object NavigateBack : MoviePreviewSideEffect()
    data object ShowLoader : MoviePreviewSideEffect()
    data object HideLoaderWithSuccess : MoviePreviewSideEffect()
    data class HideLoaderWithError(val error: Throwable) : MoviePreviewSideEffect()
}

@MviState
data class MoviePreviewState(
    val movie: MovieDetails? = null,
    val castData: CastData? = null,
    val isLoading: Boolean = false,
    val isMovieAdded: Boolean = false,
)

fun MoviePreviewState.isSuccess(): Boolean {
    return !isLoading && movie != null && castData != null
}
