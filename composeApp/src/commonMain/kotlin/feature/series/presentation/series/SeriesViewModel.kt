package feature.series.presentation.series

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import feature.series.data.repository.SeriesRepository
import feature.series.presentation.series.SeriesIntent.AddSeriesPressed
import feature.series.presentation.series.SeriesIntent.SeriesPressed
import feature.series.presentation.series.SeriesIntent.Refresh
import feature.series.presentation.series.SeriesSideEffect.GoToAddSeries
import feature.series.presentation.series.SeriesSideEffect.GoToSeriesDetail
import feature.series.presentation.series.SeriesState.Idle
import feature.series.presentation.series.SeriesState.Loading
import feature.series.presentation.series.SeriesState.Success
import feature.series.presentation.series.SeriesState.Error
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

class SeriesViewModel(
    private val repository: SeriesRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SeriesIntent, SeriesSideEffect, SeriesState>() {

    private var loadSeriesJob: Job? = null

    override fun getDefaultState() = Idle

    override fun processIntent(intent: SeriesIntent) {
        when (intent) {
            Refresh -> getSeries()
            is SeriesPressed -> sendSideEffect(GoToSeriesDetail(intent.series))
            is AddSeriesPressed -> sendSideEffect(GoToAddSeries)
        }
    }

    fun getSeries() {
        if (loadSeriesJob?.isActive == true) return
        if (viewState.value.isIdle()) updateViewState { Loading }

        loadSeriesJob = screenModelScope.launch(dispatchersProvider.io) {
            when (val result = repository.getFirebaseSeries()) {
                is Response.Success -> updateViewState { Success(result.data) }
                is Response.Failure -> updateViewState { Error(result.error) }
            }
        }
    }
}
