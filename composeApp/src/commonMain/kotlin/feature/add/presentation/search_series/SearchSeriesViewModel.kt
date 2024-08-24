package feature.add.presentation.search_series

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshSeriesList
import core.utils.Resource
import feature.add.data.series.SeriesPagingSource
import feature.series.data.api.SeriesApi
import feature.series.data.repository.SeriesRepository
import feature.series.domain.model.Series
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchSeriesViewModel(
    private val seriesApi: SeriesApi,
    private val seriesRepository: SeriesRepository,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SearchSeriesIntent, SearchSeriesSideEffect, SearchSeriesState>() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    val pager: Flow<PagingData<Series>> = _searchQuery
        .filterNotNull()
        .debounce(1000)
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                updateViewState { copy(isSearchInitiated = false) }
                flowOf(PagingData.empty())
            } else {
                updateViewState { copy(isSearchInitiated = true) }
                Pager(
                    config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = { SeriesPagingSource(seriesApi, query) }
                ).flow.cachedIn(screenModelScope)
            }
        }
        .stateIn(screenModelScope, SharingStarted.Lazily, PagingData.empty())

    override fun getDefaultState() = SearchSeriesState()

    override fun processIntent(intent: SearchSeriesIntent) {
        when (intent) {
            is SearchSeriesIntent.QueryChanged -> updateQuery(intent.query)
            is SearchSeriesIntent.SeriesPressed -> addSeries(intent.series)
            is SearchSeriesIntent.ClearQueryPressed -> {
                updateViewState { SearchSeriesState() }
                _searchQuery.value = ""
            }
        }
    }

    private fun addSeries(series: Series) {
        sendSideEffect(SearchSeriesSideEffect.ShowLoader)
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = seriesRepository.addFirebaseSeries(series)) {
                is Resource.Success -> {
                    sendSideEffect(SearchSeriesSideEffect.HideLoaderWithSuccess)
                    eventBus.invokeEvent(RefreshSeriesList)
                }
                is Resource.Failure -> {
                    sendSideEffect(SearchSeriesSideEffect.HideLoaderWithError(result.error))
                }
                else -> {
                    // NO - OP
                }
            }
        }
    }

    private fun updateQuery(query: String) {
        updateViewState { copy(query = query) }
        _searchQuery.value = query
    }
}
