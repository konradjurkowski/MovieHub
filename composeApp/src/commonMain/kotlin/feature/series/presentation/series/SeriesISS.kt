package feature.series.presentation.series

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.utils.Resource
import feature.series.domain.model.FirebaseSeries

@MviIntent
sealed class SeriesIntent {
    data class SeriesPressed(val series: FirebaseSeries) : SeriesIntent()
    data object Refresh : SeriesIntent()
}

@MviSideEffect
sealed class SeriesSideEffect {
    data class GoToSeriesDetail(val series: FirebaseSeries) : SeriesSideEffect()
}

typealias SeriesState = Resource<List<FirebaseSeries>>
