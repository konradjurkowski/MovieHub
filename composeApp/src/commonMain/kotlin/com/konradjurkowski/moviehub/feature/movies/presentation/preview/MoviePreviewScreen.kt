package com.konradjurkowski.moviehub.feature.movies.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.core.utils.extensions.showError
import com.konradjurkowski.moviehub.core.presentation.comp.loading.LoadingOverlay
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.comp.MoviePreviewContent
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewEvent.ShowError
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewEvent.ShowSuccess
import com.konradjurkowski.snackbarkmm.LocalSnackbarState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.movie_screen_preview_add_success
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoviePreviewScreen(viewModel: MoviePreviewViewModel = koinViewModel()) {
    val snackBarState = LocalSnackbarState.current
    val state by viewModel.viewState.collectAsState()

    CollectEvents(viewModel.viewEvents) { event ->
        when (event) {
            is ShowError -> snackBarState.showError(event.error)
            is ShowSuccess -> snackBarState.showSuccess(Res.string.movie_screen_preview_add_success)
        }
    }

    MoviePreviewContent(
        state = state,
        onIntent = viewModel::sendIntent,
    )

    LoadingOverlay(loading = state.isAdding())
}
