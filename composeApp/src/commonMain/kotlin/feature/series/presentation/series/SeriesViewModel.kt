package feature.series.presentation.series

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import feature.series.data.repository.SeriesRepository
import kotlinx.coroutines.launch

class SeriesViewModel(
    private val repository: SeriesRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SeriesIntent, SeriesSideEffect, SeriesState>() {

    override fun getDefaultState() = SeriesState.Idle

    override fun processIntent(intent: SeriesIntent) {
        when (intent) {
            SeriesIntent.Refresh -> getSeries()
            is SeriesIntent.SeriesPressed -> sendSideEffect(SeriesSideEffect.GoToSeriesDetail(intent.series))
            is SeriesIntent.AddSeriesPressed -> sendSideEffect(SeriesSideEffect.GoToAddSeries)
        }
    }

    fun getSeries() {
        if (viewState.value == SeriesState.Idle) updateViewState { SeriesState.Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = repository.getFirebaseSeries()) {
                is Response.Success -> updateViewState { SeriesState.Success(result.data) }
                is Response.Failure -> updateViewState { SeriesState.Error(result.error) }
            }
        }
    }
}
