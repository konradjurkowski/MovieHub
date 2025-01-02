package feature.series.presentation.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.utils.Resource
import feature.series.data.paging.SeriesPagingSource
import feature.series.data.api.SeriesApi
import feature.series.data.paging.PopularSeriesPagingSource
import feature.series.data.repository.SeriesRepository
import feature.series.data.storage.SeriesRegistry
import feature.series.domain.model.Series
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchSeriesViewModel(
    private val seriesApi: SeriesApi,
    private val seriesRepository: SeriesRepository,
    private val seriesRegistry: SeriesRegistry,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SearchSeriesIntent, SearchSeriesSideEffect, SearchSeriesState>() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    val pager: Flow<PagingData<Series>> = _searchQuery
        .debounce(500)
        .flatMapLatest { query ->
            val pagingSource = when {
                query?.isNotEmpty() == true -> SeriesPagingSource(api = seriesApi, query = query)
                else -> PopularSeriesPagingSource(api = seriesApi)
            }
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { pagingSource }
            ).flow.cachedIn(screenModelScope)
        }
        .stateIn(screenModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        initializeListeners()
        _searchQuery.value = ""
    }

    override fun getDefaultState() = SearchSeriesState()

    override fun processIntent(intent: SearchSeriesIntent) {
        when (intent) {
            is SearchSeriesIntent.QueryChanged -> updateQuery(intent.query)
            is SearchSeriesIntent.SeriesAddPressed -> addSeries(intent.series)
            is SearchSeriesIntent.SeriesCardPressed -> sendSideEffect(
                SearchSeriesSideEffect.GoToSeriesPreview(intent.series))
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
                    seriesRegistry.addSeries(series.id)
                    sendSideEffect(SearchSeriesSideEffect.HideLoaderWithSuccess)
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

    private fun initializeListeners() {
        seriesRegistry.series.onEach { seriesIds ->
            updateViewState { copy(addedSeriesIds = seriesIds) }
        }.launchIn(screenModelScope)
    }
}
