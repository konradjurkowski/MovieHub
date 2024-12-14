package feature.movies.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import core.utils.LocalLoaderState
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.movies.presentation.search.components.SearchMovieScreen
import feature.movies.presentation.preview.MoviePreviewScreenRoot
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.search_movie_screen_add_success
import kotlin.random.Random

class SearchMovieScreenRoot : Screen {

    override val key: ScreenKey = super.key + Random.nextDouble()

    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current
        val focusManager = LocalFocusManager.current

        val viewModel = getScreenModel<SearchMovieViewModel>()
        val state by viewModel.viewState.collectAsState()
        val pagingMovies = viewModel.pager.collectAsLazyPagingItems()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is SearchMovieSideEffect.HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
                }

                is SearchMovieSideEffect.GoToMoviePreview -> {
                    focusManager.clearFocus()
                    GlobalNavigators.navigator?.push(MoviePreviewScreenRoot(effect.movie.id))
                }

                SearchMovieSideEffect.HideLoaderWithSuccess -> {
                    loaderState.hideLoader()
                    snackbarState.showSuccess(Res.string.search_movie_screen_add_success)
                }

                SearchMovieSideEffect.ShowLoader -> loaderState.showLoader()
            }
        }

        SearchMovieScreen(
            pagingMovies = pagingMovies,
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
