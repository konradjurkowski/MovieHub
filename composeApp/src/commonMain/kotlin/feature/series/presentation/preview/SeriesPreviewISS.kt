package feature.series.presentation.preview

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import core.model.media.CastData
import core.model.media.Video
import feature.series.domain.model.SeriesDetails

@MviIntent
sealed class SeriesPreviewIntent {
    data object BackPressed : SeriesPreviewIntent()
    data object Refresh : SeriesPreviewIntent()
    data class SeriesAddPressed(val series: SeriesDetails) : SeriesPreviewIntent()
    data class VideoPressed(val video: Video) : SeriesPreviewIntent()
}

@MviSideEffect
sealed class SeriesPreviewSideEffect {
    data object NavigateBack : SeriesPreviewSideEffect()
    data object ShowLoader : SeriesPreviewSideEffect()
    data object HideLoaderWithSuccess : SeriesPreviewSideEffect()
    data class HideLoaderWithError(val error: Throwable) : SeriesPreviewSideEffect()
    data class OpenUrl(val url: String) : SeriesPreviewSideEffect()
}

@MviState
sealed class SeriesPreviewState {
    data object Idle : SeriesPreviewState()
    data object Loading : SeriesPreviewState()
    data class Error(val error: Throwable? = null) : SeriesPreviewState()
    data class Success(
        val series: SeriesDetails,
        val castData: CastData,
        val isSeriesAdded: Boolean = false,
    ) : SeriesPreviewState()

    fun isSuccess() = this is Success
    fun isMediaAdded(): Boolean = getSuccess()?.isSeriesAdded ?: false
    fun getSuccess(): Success? = this as? Success
}
