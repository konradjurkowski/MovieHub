package feature.series.presentation.series

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshSeriesList
import core.utils.Resource
import feature.series.data.repository.SeriesRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

class SeriesViewModel(
    private val repository: SeriesRepository,
    private val dispatchersProvider: DispatchersProvider,
    private val eventBus: EventBus,
) : BaseViewModel<SeriesIntent, SeriesSideEffect, SeriesState>() {

    init {
        initListener()
        getSeries()
    }

    override fun getDefaultState() = Resource.Idle

    override fun processIntent(intent: SeriesIntent) {
        when (intent) {
            SeriesIntent.Refresh -> getSeries()
            is SeriesIntent.SeriesPressed -> sendSideEffect(SeriesSideEffect.GoToSeriesDetail(intent.series))
        }
    }

    private fun getSeries() {
        updateViewState { Resource.Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            val result = repository.getTopRatedFirebaseSeries()
            updateViewState { result }
        }
    }

    private fun initListener() {
        screenModelScope.launch {
            eventBus.events
                .filterIsInstance<RefreshSeriesList>()
                .collectLatest {
                    getSeries()
                }
        }
    }
}
