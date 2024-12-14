package feature.series.presentation.search

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.series.domain.model.Series

@MviIntent
sealed class SearchSeriesIntent {
    data class QueryChanged(val query: String) : SearchSeriesIntent()
    data object ClearQueryPressed : SearchSeriesIntent()
    data class SeriesAddPressed(val series: Series) : SearchSeriesIntent()
    data class SeriesCardPressed(val series: Series) : SearchSeriesIntent()
}

@MviSideEffect
sealed class SearchSeriesSideEffect {
    data object ShowLoader : SearchSeriesSideEffect()
    data class GoToSeriesPreview(val series: Series) : SearchSeriesSideEffect()
    data object HideLoaderWithSuccess : SearchSeriesSideEffect()
    data class HideLoaderWithError(val error: Throwable) : SearchSeriesSideEffect()
}

@MviState
data class SearchSeriesState(
    val query: String = "",
    val isSearchInitiated: Boolean = false,
    val addedSeriesIds: List<Long> = emptyList(),
)
