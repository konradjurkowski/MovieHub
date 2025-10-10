package com.konradjurkowski.moviehub.feature.movies.presentation.add

import androidx.lifecycle.viewModelScope
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.request.toCreateRequest
import com.konradjurkowski.moviehub.feature.movies.data.paging.SearchMoviePagingSource
import com.konradjurkowski.moviehub.feature.movies.data.paging.PopularMoviePagingSource
import com.konradjurkowski.moviehub.feature.movies.domain.api.MovieApi
import com.konradjurkowski.moviehub.feature.movies.domain.model.Movie
import com.konradjurkowski.moviehub.feature.movies.domain.repository.MovieRepository
import com.konradjurkowski.moviehub.feature.movies.domain.storage.MovieStorage
import com.konradjurkowski.moviehub.feature.movies.navigation.MoviesDestination.MoviePreviewRoute
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieEvent
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieEvent.ShowError
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieEvent.ShowSuccess
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.ClearQueryPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.MovieAddPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.MovieCardPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.QueryChanged
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddMovieViewModel(
    private val movieApi: MovieApi,
    private val movieRepository: MovieRepository,
    private val movieStorage: MovieStorage,
    private val navigator: AppNavigator,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<AddMovieIntent, AddMovieState, AddMovieEvent>(
    initialState = AddMovieState(),
) {

    private val _searchQuery = MutableStateFlow("")

    private var addingJob: Job? = null

    val pager: Flow<PagingData<Movie>> = _searchQuery
        .debounce { query -> if (query.isEmpty()) 0 else 500 }
        .flatMapLatest { query ->
            val pagingSource = when {
                query.isNotEmpty() -> SearchMoviePagingSource(api = movieApi, query = query)
                else -> PopularMoviePagingSource(api = movieApi)
            }
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { pagingSource },
            ).flow.cachedIn(viewModelScope)
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        initializeListeners()
    }

    override fun processIntent(intent: AddMovieIntent) {
        when (intent) {
            is MovieAddPressed -> addMovie(intent.movie)
            is MovieCardPressed -> navigator.push(MoviePreviewRoute(tmdbId = intent.movie.tmdbId))
            is QueryChanged -> updateQuery(intent.query)
            ClearQueryPressed -> {
                val addedMovieIds = viewState.value.addedTmdbIds
                updateState { AddMovieState(addedTmdbIds = addedMovieIds) }
                _searchQuery.value = ""
            }
        }
    }

    private fun updateQuery(query: String) {
        updateState { copy(query = query) }
        _searchQuery.value = query
    }

    private fun addMovie(movie: Movie) {
        if (addingJob?.isActive == true) return

        updateState { copy(addState = ActionState.Loading) }
        addingJob = viewModelScope.launch(dispatchersProvider.io) {
            val request =movie.toCreateRequest(groupId = 1)
            when (val result = movieRepository.addMovie(request)) {
                is Response.Success -> {
                    sendEvent(ShowSuccess)
                    movieStorage.saveTmdbId(result.data.tmdbId)
                    updateState { copy(addState = ActionState.Success) }
                }

                is Response.Failure -> {
                    sendEvent(ShowError(result.error))
                    updateState { copy(addState = ActionState.Failure) }
                }
            }
        }
    }

    private fun initializeListeners() {
        movieStorage.tmdbIdsFlow.onEach { ids ->
            updateState { copy(addedTmdbIds = ids) }
        }.launchIn(viewModelScope)
    }
}
