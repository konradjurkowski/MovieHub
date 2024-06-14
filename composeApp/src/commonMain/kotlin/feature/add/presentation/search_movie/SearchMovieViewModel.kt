package feature.add.presentation.search_movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import feature.add.data.movie.MoviePagingSource
import feature.movies.data.api.MovieApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest

class SearchMovieViewModel(
    private val movieApi: MovieApi,
): BaseViewModel<SearchMovieIntent, SearchMovieSideEffect, SearchMovieState>() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    val pager = _searchQuery
        .filterNotNull()
        .filter { it.length >= 3 }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            updateViewState { copy(isSearchInitiated = true) }
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { MoviePagingSource(movieApi, query) }
            ).flow.cachedIn(screenModelScope)
        }

    override fun getDefaultState() = SearchMovieState()

    override fun processIntent(intent: SearchMovieIntent) {
        when (intent) {
            is SearchMovieIntent.QueryChanged -> updateQuery(intent.query)
            is SearchMovieIntent.SearchPressed -> search(intent.query)
        }
    }

    private fun updateQuery(query: String) {
        updateViewState { copy(query = query) }
    }

    private fun search(query: String) {
        if (_searchQuery.value == query) return
        _searchQuery.value = query
    }
}
