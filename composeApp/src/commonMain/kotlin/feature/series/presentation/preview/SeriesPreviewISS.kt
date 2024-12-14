package feature.series.presentation.preview

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.movies.domain.model.CastData
import feature.series.domain.model.SeriesDetails

@MviIntent
sealed class SeriesPreviewIntent {
    data object BackPressed : SeriesPreviewIntent()
    data object Refresh : SeriesPreviewIntent()
    data class SeriesAddPressed(val series: SeriesDetails) : SeriesPreviewIntent()
}

@MviSideEffect
sealed class SeriesPreviewSideEffect {
    data object NavigateBack : SeriesPreviewSideEffect()
    data object ShowLoader : SeriesPreviewSideEffect()
    data object HideLoaderWithSuccess : SeriesPreviewSideEffect()
    data class HideLoaderWithError(val error: Throwable) : SeriesPreviewSideEffect()
}

@MviState
data class SeriesPreviewState(
    val series: SeriesDetails? = null,
    val castData: CastData? = null,
    val isLoading: Boolean = false,
    val isSeriesAdded: Boolean = false,
)

fun SeriesPreviewState.isSuccess(): Boolean {
    return !isLoading && series != null && castData != null
}
