package feature.movies.presentation.preview

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import core.model.media.CastData
import core.model.media.Video
import feature.movies.domain.model.MovieDetails

@MviIntent
sealed class MoviePreviewIntent {
    data object BackPressed : MoviePreviewIntent()
    data object Refresh : MoviePreviewIntent()
    data class MovieAddPressed(val movie: MovieDetails) : MoviePreviewIntent()
    data class VideoPressed(val video: Video) : MoviePreviewIntent()
}

@MviSideEffect
sealed class MoviePreviewSideEffect {
    data object NavigateBack : MoviePreviewSideEffect()
    data object ShowLoader : MoviePreviewSideEffect()
    data object HideLoaderWithSuccess : MoviePreviewSideEffect()
    data class HideLoaderWithError(val error: Throwable) : MoviePreviewSideEffect()
    data class OpenUrl(val url: String) : MoviePreviewSideEffect()
}

@MviState
sealed class MoviePreviewState {
    data object Idle : MoviePreviewState()
    data object Loading : MoviePreviewState()
    data class Error(val error: Throwable? = null) : MoviePreviewState()
    data class Success(
        val movie: MovieDetails,
        val castData: CastData,
        val isMovieAdded: Boolean = false,
    ) : MoviePreviewState()

    fun isSuccess() = this is Success
    fun isMediaAdded(): Boolean = getSuccess()?.isMovieAdded ?: false
    fun getSuccess(): Success? = this as? Success
}
