package feature.series.presentation.series

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import feature.series.data.repository.SeriesRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

class SeriesViewModel(
    private val repository: SeriesRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SeriesIntent, SeriesSideEffect, SeriesState>() {

    private var loadSeriesJob: Job? = null

    override fun getDefaultState() = SeriesState.Idle

    override fun processIntent(intent: SeriesIntent) {
        when (intent) {
            SeriesIntent.Refresh -> getSeries()
            is SeriesIntent.SeriesPressed -> sendSideEffect(SeriesSideEffect.GoToSeriesDetail(intent.series))
            is SeriesIntent.AddSeriesPressed -> sendSideEffect(SeriesSideEffect.GoToAddSeries)
        }
    }

    fun getSeries() {
        if (loadSeriesJob?.isActive == true) return
        if (viewState.value.isIdle()) updateViewState { SeriesState.Loading }

        loadSeriesJob = screenModelScope.launch(dispatchersProvider.io) {
            when (val result = repository.getFirebaseSeries()) {
                is Response.Success -> updateViewState { SeriesState.Success(result.data) }
                is Response.Failure -> updateViewState { SeriesState.Error(result.error) }
            }
        }
    }
}
