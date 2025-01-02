package feature.movies.presentation.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.utils.Resource
import feature.movies.data.paging.MoviePagingSource
import feature.movies.data.api.MovieApi
import feature.movies.data.paging.PopularMoviePagingSource
import feature.movies.data.repository.MovieRepository
import feature.movies.data.storage.MovieRegistry
import feature.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchMovieViewModel(
    private val movieApi: MovieApi,
    private val movieRepository: MovieRepository,
    private val movieRegistry: MovieRegistry,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SearchMovieIntent, SearchMovieSideEffect, SearchMovieState>() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    val pager: Flow<PagingData<Movie>> = _searchQuery
        .debounce(500)
        .flatMapLatest { query ->
            val pagingSource = when {
                query?.isNotEmpty() == true -> MoviePagingSource(api = movieApi, query = query)
                else -> PopularMoviePagingSource(api = movieApi)
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

    override fun getDefaultState() = SearchMovieState()

    override fun processIntent(intent: SearchMovieIntent) {
        when (intent) {
            is SearchMovieIntent.QueryChanged -> updateQuery(intent.query)
            is SearchMovieIntent.MovieAddPressed -> addMovie(intent.movie)
            is SearchMovieIntent.MovieCardPressed -> sendSideEffect(
                SearchMovieSideEffect.GoToMoviePreview(intent.movie)
            )
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
                    movieRegistry.addMovie(movie.id)
                    sendSideEffect(SearchMovieSideEffect.HideLoaderWithSuccess)
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

    private fun initializeListeners() {
        movieRegistry.movies.onEach { movieIds ->
            updateViewState { copy(addedMovieIds = movieIds) }
        }.launchIn(screenModelScope)
    }
}
