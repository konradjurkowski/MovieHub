package feature.series.presentation.series

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.series.domain.model.FirebaseSeries

@MviIntent
sealed class SeriesIntent {
    data class SeriesPressed(val series: FirebaseSeries) : SeriesIntent()
    data object AddSeriesPressed : SeriesIntent()
    data object Refresh : SeriesIntent()
}

@MviSideEffect
sealed class SeriesSideEffect {
    data class GoToSeriesDetail(val series: FirebaseSeries) : SeriesSideEffect()
    data object GoToAddSeries : SeriesSideEffect()
}

@MviState
sealed class SeriesState {
    data object Idle : SeriesState()
    data object Loading : SeriesState()
    data class Error(val error: Throwable? = null) : SeriesState()
    data class Success(val series: List<FirebaseSeries>) : SeriesState()

    fun isIdle() = this is Idle
}
