package feature.series.presentation.details

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
import feature.rating.presentation.add_rating.AddRatingScreenRoot
import feature.series.presentation.details.SeriesDetailsSideEffect.GoToAddComment
import feature.series.presentation.details.SeriesDetailsSideEffect.HideLoaderWithError
import feature.series.presentation.details.SeriesDetailsSideEffect.HideLoaderWithSuccess
import feature.series.presentation.details.SeriesDetailsSideEffect.NavigateBack
import feature.series.presentation.details.SeriesDetailsSideEffect.OpenUrl
import feature.series.presentation.details.SeriesDetailsSideEffect.ShowLoader
import feature.series.presentation.details.components.SeriesDetailsScreen
import org.koin.core.parameter.parametersOf

class SeriesDetailsScreenRoot(val seriesId: Long) : BaseScreen() {

    @Composable
    override fun Content() {
        val uriHandler = LocalUriHandler.current
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current

        val viewModel = getScreenModel<SeriesDetailsViewModel> { parametersOf(seriesId) }
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                HideLoaderWithSuccess -> loaderState.hideLoader()
                NavigateBack -> GlobalNavigators.navigator?.pop()
                ShowLoader -> loaderState.showLoader()
                is OpenUrl -> uriHandler.openUri(effect.url)

                is GoToAddComment -> {
                    GlobalNavigators.navigator?.push(
                        AddRatingScreenRoot(
                            mediaId = seriesId,
                            isMovie = false,
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

        SeriesDetailsScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
