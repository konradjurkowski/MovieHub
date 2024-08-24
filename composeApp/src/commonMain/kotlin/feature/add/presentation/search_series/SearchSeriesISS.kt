package feature.add.presentation.search_series

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.series.domain.model.Series

@MviIntent
sealed class SearchSeriesIntent {
    data class QueryChanged(val query: String) : SearchSeriesIntent()
    data object ClearQueryPressed : SearchSeriesIntent()
    data class SeriesPressed(val series: Series) : SearchSeriesIntent()
}

@MviSideEffect
sealed class SearchSeriesSideEffect {
    data object ShowLoader : SearchSeriesSideEffect()
    data object HideLoaderWithSuccess : SearchSeriesSideEffect()
    data class HideLoaderWithError(val error: Throwable) : SearchSeriesSideEffect()
}

@MviState
data class SearchSeriesState(
    val query: String = "",
    val isSearchInitiated: Boolean = false,
)
