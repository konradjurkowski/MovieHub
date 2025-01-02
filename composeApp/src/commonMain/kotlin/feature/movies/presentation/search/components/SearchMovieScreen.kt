package feature.movies.presentation.search.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import core.components.media.PagingMediaList
import core.components.top_bar.SearchTopBar
import core.utils.clearFocus
import feature.movies.presentation.search.SearchMovieIntent
import feature.movies.presentation.search.SearchMovieState
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
                onValueChange = { onIntent(SearchMovieIntent.QueryChanged(it)) },
                onClearPressed = { onIntent(SearchMovieIntent.ClearQueryPressed) },
            )
        },
    ) { contentPadding ->
        PagingMediaList(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            pagingMedia = pagingMovies,
            isMediaAdded = { movie -> state.addedMovieIds.contains(movie.id) },
            onAddClick = { onIntent(SearchMovieIntent.MovieAddPressed(it)) },
            onCardClick = { onIntent(SearchMovieIntent.MovieCardPressed(it)) },
        )
    }
}
