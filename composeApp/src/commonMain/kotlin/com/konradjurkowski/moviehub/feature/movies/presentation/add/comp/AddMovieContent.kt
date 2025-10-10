package com.konradjurkowski.moviehub.feature.movies.presentation.add.comp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import com.konradjurkowski.moviehub.core.presentation.comp.media.paging.GridMediaPagingItem
import com.konradjurkowski.moviehub.core.presentation.comp.media.paging.GridMediaPagingList
import com.konradjurkowski.moviehub.core.presentation.comp.top_bar.SearchTopBar
import com.konradjurkowski.moviehub.core.utils.extensions.clearFocus
import com.konradjurkowski.moviehub.feature.movies.domain.model.Movie
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.ClearQueryPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.MovieAddPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.MovieCardPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieIntent.QueryChanged
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieState

@Composable
fun AddMovieContent(
    pagingMovies: LazyPagingItems<Movie>,
    state: AddMovieState,
    onIntent: (AddMovieIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.clearFocus(),
        topBar = {
            SearchTopBar(
                value = state.query,
                requestFocus = true,
                onValueChange = { onIntent(QueryChanged(it)) },
                onClearPressed = { onIntent(ClearQueryPressed) },
            )
        },
    ) { innerPadding ->
        GridMediaPagingList(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            pagingItems = pagingMovies,
            itemContent = { movie ->
                GridMediaPagingItem(
                    imageUrl = movie.posterUrl,
                    isAdded = state.addedTmdbIds.contains(movie.tmdbId),
                    onAddClick = { onIntent(MovieAddPressed(movie)) },
                    onCardClick = { onIntent(MovieCardPressed(movie)) },
                )
            },
        )
    }
}
