package com.konradjurkowski.moviehub.feature.movies.presentation.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.comp.MoviesContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoviesScreen() {
    val viewModel = koinViewModel<MoviesViewModel>()
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMovies()
    }

    MoviesContent(
        state = state,
        onIntent = viewModel::sendIntent,
    )
}
