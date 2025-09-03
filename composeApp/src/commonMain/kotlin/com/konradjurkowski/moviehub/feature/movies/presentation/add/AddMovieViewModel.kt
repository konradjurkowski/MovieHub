package com.konradjurkowski.moviehub.feature.movies.presentation.add

import androidx.lifecycle.viewModelScope
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.utils.tools.DispatchersProvider
import com.konradjurkowski.moviehub.feature.movies.data.paging.SearchMoviePagingSource
import com.konradjurkowski.moviehub.feature.movies.data.paging.PopularMoviePagingSource
import com.konradjurkowski.moviehub.feature.movies.domain.api.MovieApi
import com.konradjurkowski.moviehub.feature.movies.domain.model.Movie
import com.konradjurkowski.moviehub.feature.movies.navigation.MoviesDestination.MoviePreviewRoute
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieEvent
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.ClearQueryPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.MovieAddPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.MovieCardPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.QueryChanged
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class AddMovieViewModel(
    private val movieApi: MovieApi,
    private val navigator: AppNavigator,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<AddMovieIntent, AddMovieState, AddMovieEvent>(
    initialState = AddMovieState(),
) {

    private val _searchQuery = MutableStateFlow("")

    val pager: Flow<PagingData<Movie>> = _searchQuery
        .debounce(500)
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

    override fun processIntent(intent: AddMovieIntent) {
        when (intent) {
            is MovieAddPressed -> {}
            is MovieCardPressed -> navigator.push(MoviePreviewRoute(movieId = intent.movie.id))
            is QueryChanged -> updateQuery(intent.query)
            ClearQueryPressed -> {
                val addedMovieIds = viewState.value.addedMovieIds
                updateState { AddMovieState(addedMovieIds = addedMovieIds) }
                _searchQuery.value = ""
            }
        }
    }

    private fun updateQuery(query: String) {
        updateState { copy(query = query) }
        _searchQuery.value = query
    }
}
