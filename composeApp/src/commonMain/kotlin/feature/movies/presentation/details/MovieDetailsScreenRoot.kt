package feature.movies.presentation.details

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
import core.utils.safePush
import feature.rating.presentation.add_rating.AddRatingScreenRoot
import feature.movies.presentation.details.components.MovieDetailsScreen
import feature.movies.presentation.details.MovieDetailsSideEffect.GoToAddComment
import feature.movies.presentation.details.MovieDetailsSideEffect.HideLoaderWithError
import feature.movies.presentation.details.MovieDetailsSideEffect.HideLoaderWithSuccess
import feature.movies.presentation.details.MovieDetailsSideEffect.NavigateBack
import feature.movies.presentation.details.MovieDetailsSideEffect.OpenUrl
import feature.movies.presentation.details.MovieDetailsSideEffect.ShowLoader
import org.koin.core.parameter.parametersOf

class MovieDetailsScreenRoot(val movieId: Long) : BaseScreen() {

    @Composable
    override fun Content() {
        val uriHandler = LocalUriHandler.current
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current

        val viewModel = getScreenModel<MovieDetailsViewModel> { parametersOf(movieId) }
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                HideLoaderWithSuccess -> loaderState.hideLoader()
                NavigateBack -> GlobalNavigators.navigator?.pop()
                ShowLoader -> loaderState.showLoader()
                is OpenUrl -> uriHandler.openUri(effect.url)

                is GoToAddComment -> {
                    GlobalNavigators.navigator?.safePush(
                        AddRatingScreenRoot(
                            mediaId = movieId,
                            firebaseRating = effect.firebaseRating,
                        )
                    )
                }

                is HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
                }
            }
        }

        MovieDetailsScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}


