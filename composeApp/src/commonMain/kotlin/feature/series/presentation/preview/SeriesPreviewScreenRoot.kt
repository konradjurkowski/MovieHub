package feature.series.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import core.utils.LocalLoaderState
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.series.presentation.preview.components.SeriesPreviewScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.series_screen_preview_add_success
import org.koin.core.parameter.parametersOf

class SeriesPreviewScreenRoot(val seriesId: Long) : BaseScreen() {

    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current

        val viewModel = getScreenModel<SeriesPreviewViewModel> { parametersOf(seriesId) }
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                SeriesPreviewSideEffect.NavigateBack -> GlobalNavigators.navigator?.pop()

                SeriesPreviewSideEffect.HideLoaderWithSuccess -> {
                    loaderState.hideLoader()
                    snackbarState.showSuccess(Res.string.series_screen_preview_add_success)
                }

                is SeriesPreviewSideEffect.HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
                }

                is SeriesPreviewSideEffect.ShowLoader -> loaderState.hideLoader()
            }
        }

        SeriesPreviewScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
