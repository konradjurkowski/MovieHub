package com.konradjurkowski.moviehub.feature.movies.presentation.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import com.konradjurkowski.moviehub.feature.movies.presentation.add.comp.AddMovieContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddMovieScreen(viewModel: AddMovieViewModel = koinViewModel()) {
    val state by viewModel.viewState.collectAsState()
    val pagingMovies = viewModel.pager.collectAsLazyPagingItems()

    AddMovieContent(
        pagingMovies = pagingMovies,
        state = state,
        onIntent = viewModel::sendIntent,
    )
}
