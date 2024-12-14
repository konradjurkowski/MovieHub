package feature.movies.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import core.utils.LocalLoaderState
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.movies.presentation.preview.components.MoviePreviewScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.movie_screen_preview_add_success
import org.koin.core.parameter.parametersOf

class MoviePreviewScreenRoot(val movieId: Long) : Screen {

    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current

        val viewModel = getScreenModel<MoviePreviewViewModel> { parametersOf(movieId) }
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                MoviePreviewSideEffect.NavigateBack -> GlobalNavigators.navigator?.pop()

                MoviePreviewSideEffect.HideLoaderWithSuccess -> {
                    loaderState.hideLoader()
                    snackbarState.showSuccess(Res.string.movie_screen_preview_add_success)
                }

                is MoviePreviewSideEffect.HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
                }

                is MoviePreviewSideEffect.ShowLoader -> loaderState.showLoader()
            }
        }

        MoviePreviewScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
