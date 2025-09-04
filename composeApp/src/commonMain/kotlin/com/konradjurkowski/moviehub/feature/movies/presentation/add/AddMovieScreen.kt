package com.konradjurkowski.moviehub.feature.movies.presentation.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.core.presentation.comp.loading.LoadingOverlay
import com.konradjurkowski.moviehub.core.utils.helpers.showError
import com.konradjurkowski.moviehub.feature.movies.presentation.add.comp.AddMovieContent
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieEvent.ShowError
import com.konradjurkowski.moviehub.feature.movies.presentation.add.ise.AddMovieEvent.ShowSuccess
import com.konradjurkowski.snackbarkmm.LocalSnackbarState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.search_movie_screen_add_success
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddMovieScreen(viewModel: AddMovieViewModel = koinViewModel()) {
    val snackBarState = LocalSnackbarState.current
    val state by viewModel.viewState.collectAsState()
    val pagingMovies = viewModel.pager.collectAsLazyPagingItems()

    CollectEvents(viewModel.viewEvents) { event ->
        when (event) {
            is ShowError -> snackBarState.showError(event.error)
            ShowSuccess -> snackBarState.showSuccess(Res.string.search_movie_screen_add_success)
        }
    }

    AddMovieContent(
        pagingMovies = pagingMovies,
        state = state,
        onIntent = viewModel::sendIntent,
    )

    LoadingOverlay(loading = state.addState.isLoading())
}
