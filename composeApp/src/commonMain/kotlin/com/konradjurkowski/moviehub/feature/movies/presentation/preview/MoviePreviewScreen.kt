package com.konradjurkowski.moviehub.feature.movies.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.comp.MoviePreviewContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoviePreviewScreen(viewModel: MoviePreviewViewModel = koinViewModel()) {
    val state by viewModel.viewState.collectAsState()

    MoviePreviewContent(
        state = state,
        onIntent = viewModel::sendIntent,
    )
}
