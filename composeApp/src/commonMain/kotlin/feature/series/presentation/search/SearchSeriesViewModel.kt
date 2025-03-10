package feature.series.presentation.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import feature.series.data.paging.SeriesPagingSource
import feature.series.data.api.SeriesApi
import feature.series.data.paging.PopularSeriesPagingSource
import feature.series.data.repository.SeriesRepository
import feature.series.data.storage.SeriesRegistry
import feature.series.domain.model.Series
import feature.series.presentation.search.SearchSeriesIntent.ClearQueryPressed
import feature.series.presentation.search.SearchSeriesIntent.SeriesAddPressed
import feature.series.presentation.search.SearchSeriesIntent.SeriesCardPressed
import feature.series.presentation.search.SearchSeriesIntent.QueryChanged
import feature.series.presentation.search.SearchSeriesSideEffect.GoToSeriesPreview
import feature.series.presentation.search.SearchSeriesSideEffect.HideLoaderWithError
import feature.series.presentation.search.SearchSeriesSideEffect.HideLoaderWithSuccess
import feature.series.presentation.search.SearchSeriesSideEffect.ShowLoader
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
            is QueryChanged -> updateQuery(intent.query)
            is SeriesAddPressed -> addSeries(intent.series)
            is SeriesCardPressed -> sendSideEffect(GoToSeriesPreview(intent.series))

            is ClearQueryPressed -> {
                val addedSeriesIds = viewState.value.addedSeriesIds
                updateViewState { SearchSeriesState(addedSeriesIds = addedSeriesIds) }
                _searchQuery.value = ""
            }
        }
    }

    private fun addSeries(series: Series) {
        sendSideEffect(ShowLoader)
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = seriesRepository.addFirebaseSeries(series)) {
                is Response.Success -> {
                    seriesRegistry.addSeries(series.id)
                    sendSideEffect(HideLoaderWithSuccess)
                }
                is Response.Failure -> {
                    sendSideEffect(HideLoaderWithError(result.error))
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
