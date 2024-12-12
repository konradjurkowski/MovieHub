package feature.series.presentation.series_details

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
import feature.series.presentation.series_details.components.SeriesDetailsScreen
import org.koin.core.parameter.parametersOf

class SeriesDetailsScreenRoot(val seriesId: Long) : Screen {

    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current

        val viewModel = getScreenModel<SeriesDetailsViewModel> { parametersOf(seriesId) }
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is SeriesDetailsSideEffect.GoToAddComment -> {
                    GlobalNavigators.navigator?.push(
                        AddRatingScreenRoot(
                            mediaId = seriesId,
                            isMovie = false,
                            firebaseRating = effect.firebaseRating,
                        )
                    )
                }
                is SeriesDetailsSideEffect.HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
                }
                SeriesDetailsSideEffect.HideLoaderWithSuccess -> loaderState.hideLoader()
                SeriesDetailsSideEffect.NavigateBack -> GlobalNavigators.navigator?.pop()
                SeriesDetailsSideEffect.ShowLoader -> loaderState.showLoader()
            }
        }

        SeriesDetailsScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
