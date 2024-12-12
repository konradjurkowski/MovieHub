package feature.add.presentation.search_movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshMovieList
import core.utils.Resource
import feature.movies.data.api.MoviePagingSource
import feature.movies.data.api.MovieApi
import feature.movies.data.repository.MovieRepository
import feature.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchMovieViewModel(
    private val movieApi: MovieApi,
    private val movieRepository: MovieRepository,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SearchMovieIntent, SearchMovieSideEffect, SearchMovieState>() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    val pager: Flow<PagingData<Movie>> = _searchQuery
        .filterNotNull()
        .debounce(500)
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                updateViewState { copy(isSearchInitiated = false) }
                flowOf(PagingData.empty())
            } else {
                updateViewState { copy(isSearchInitiated = true) }
                Pager(
                    config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = { MoviePagingSource(movieApi, query) }
                ).flow.cachedIn(screenModelScope)
            }
        }
        .stateIn(screenModelScope, SharingStarted.Lazily, PagingData.empty())

    override fun getDefaultState() = SearchMovieState()

    override fun processIntent(intent: SearchMovieIntent) {
        when (intent) {
            is SearchMovieIntent.QueryChanged -> updateQuery(intent.query)
            is SearchMovieIntent.MoviePressed -> addMovie(intent.movie)
            SearchMovieIntent.ClearQueryPressed -> {
                updateViewState { SearchMovieState() }
                _searchQuery.value = ""
            }
        }
    }

    private fun addMovie(movie: Movie) {
        sendSideEffect(SearchMovieSideEffect.ShowLoader)
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = movieRepository.addFirebaseMovie(movie)) {
                is Resource.Success -> {
                    sendSideEffect(SearchMovieSideEffect.HideLoaderWithSuccess)
                    eventBus.invokeEvent(RefreshMovieList)
                }
                is Resource.Failure -> {
                    sendSideEffect(SearchMovieSideEffect.HideLoaderWithError(result.error))
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
