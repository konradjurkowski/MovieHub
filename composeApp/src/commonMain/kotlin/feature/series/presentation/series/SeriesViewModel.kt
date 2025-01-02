package feature.series.presentation.series

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.utils.Resource
import feature.series.data.repository.SeriesRepository
import kotlinx.coroutines.launch

class SeriesViewModel(
    private val repository: SeriesRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SeriesIntent, SeriesSideEffect, SeriesState>() {

    override fun getDefaultState() = Resource.Idle

    override fun processIntent(intent: SeriesIntent) {
        when (intent) {
            SeriesIntent.Refresh -> getSeries()
            is SeriesIntent.SeriesPressed -> sendSideEffect(SeriesSideEffect.GoToSeriesDetail(intent.series))
            is SeriesIntent.AddSeriesPressed -> sendSideEffect(SeriesSideEffect.GoToAddSeries)
        }
    }

    fun getSeries() {
        if (viewState.value == Resource.Idle) updateViewState { Resource.Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            val result = repository.getTopRatedFirebaseSeries()
            updateViewState { result }
        }
    }
}
