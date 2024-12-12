package feature.add.presentation.search_movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.CollectSideEffects
import core.utils.LocalLoaderState
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.add.presentation.search_movie.components.SearchMovieScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.search_movie_screen_add_success
import kotlin.random.Random

class SearchMovieScreenRoot : Screen {

    override val key: ScreenKey = super.key + Random.nextDouble()

    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current

        val viewModel = getScreenModel<SearchMovieViewModel>()
        val state by viewModel.viewState.collectAsState()
        val pagingMovies = viewModel.pager.collectAsLazyPagingItems()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is SearchMovieSideEffect.HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
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
