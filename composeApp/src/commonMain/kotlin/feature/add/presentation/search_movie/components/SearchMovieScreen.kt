package feature.add.presentation.search_movie.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import app.cash.paging.compose.LazyPagingItems
import core.components.top_bar.SearchTopBar
import core.utils.clearFocus
import feature.add.presentation.search_movie.SearchMovieIntent
import feature.add.presentation.search_movie.SearchMovieState
import feature.movies.domain.model.Movie

@Composable
fun SearchMovieScreen(
    pagingMovies: LazyPagingItems<Movie>,
    state: SearchMovieState,
    onIntent: (SearchMovieIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.clearFocus(),
        topBar = {
            SearchTopBar(
                value = state.query,
                isButtonEnabled = state.query.length >= 3,
                onValueChanged = { onIntent(SearchMovieIntent.QueryChanged(it)) },
                onSearchPressed = {
                    focusManager.clearFocus()
                    onIntent(SearchMovieIntent.SearchPressed(state.query))
                },
            )
        },
    ) { contentPadding ->
        if (state.isSearchInitiated) {
            PagingMovieList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                pagingMovies = pagingMovies,
            )
        }
    }
}
