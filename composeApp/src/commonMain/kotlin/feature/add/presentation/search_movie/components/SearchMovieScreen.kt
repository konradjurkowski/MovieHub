package feature.add.presentation.search_movie.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import core.components.media.PagingMediaList
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
    Scaffold(
        modifier = Modifier.clearFocus(),
        topBar = {
            SearchTopBar(
                value = state.query,
                shouldRequestFocus = !state.isSearchInitiated,
                onValueChange = { onIntent(SearchMovieIntent.QueryChanged(it)) },
                onClearPressed = { onIntent(SearchMovieIntent.ClearQueryPressed) },
            )
        },
    ) { contentPadding ->
        if (state.isSearchInitiated) {
            PagingMediaList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                pagingMedia = pagingMovies,
                onClick = { onIntent(SearchMovieIntent.MoviePressed(it)) },
            )
        }
    }
}
