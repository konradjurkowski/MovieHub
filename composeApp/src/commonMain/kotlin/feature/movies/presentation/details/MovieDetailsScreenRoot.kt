package feature.movies.presentation.details

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
import feature.rating.presentation.add_rating.AddRatingScreenRoot
import feature.movies.presentation.details.components.MovieDetailsScreen
import org.koin.core.parameter.parametersOf

class MovieDetailsScreenRoot(val movieId: Long) : Screen {

    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current

        val viewModel = getScreenModel<MovieDetailsViewModel> { parametersOf(movieId) }
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is MovieDetailsSideEffect.GoToAddComment -> {
                    GlobalNavigators.navigator?.push(
                        AddRatingScreenRoot(
                            mediaId = movieId,
                            firebaseRating = effect.firebaseRating,
                        )
                    )
                }
                is MovieDetailsSideEffect.HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
                }
                MovieDetailsSideEffect.HideLoaderWithSuccess -> loaderState.hideLoader()
                MovieDetailsSideEffect.NavigateBack -> GlobalNavigators.navigator?.pop()
                MovieDetailsSideEffect.ShowLoader -> loaderState.showLoader()
            }
        }

        MovieDetailsScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}


