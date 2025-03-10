package feature.movies.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import core.utils.LocalLoaderState
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.movies.presentation.preview.components.MoviePreviewScreen
import feature.movies.presentation.preview.MoviePreviewSideEffect.HideLoaderWithError
import feature.movies.presentation.preview.MoviePreviewSideEffect.HideLoaderWithSuccess
import feature.movies.presentation.preview.MoviePreviewSideEffect.NavigateBack
import feature.movies.presentation.preview.MoviePreviewSideEffect.OpenUrl
import feature.movies.presentation.preview.MoviePreviewSideEffect.ShowLoader
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.movie_screen_preview_add_success
import org.koin.core.parameter.parametersOf

class MoviePreviewScreenRoot(val movieId: Long) : BaseScreen() {

    @Composable
    override fun Content() {
        val uriHandler = LocalUriHandler.current
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current

        val viewModel = getScreenModel<MoviePreviewViewModel> { parametersOf(movieId) }
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                NavigateBack -> GlobalNavigators.navigator?.pop()
                is ShowLoader -> loaderState.showLoader()
                is OpenUrl -> uriHandler.openUri(effect.url)

                HideLoaderWithSuccess -> {
                    loaderState.hideLoader()
                    snackbarState.showSuccess(Res.string.movie_screen_preview_add_success)
                }

                is HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
                }
            }
        }

        MoviePreviewScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
