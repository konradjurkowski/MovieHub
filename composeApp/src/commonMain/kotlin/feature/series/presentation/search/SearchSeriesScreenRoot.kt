package feature.series.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import core.utils.LocalLoaderState
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.series.presentation.preview.SeriesPreviewScreenRoot
import feature.series.presentation.search.components.SearchSeriesScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.search_series_screen_add_success

class SearchSeriesScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current
        val focusManager = LocalFocusManager.current

        val viewModel = getScreenModel<SearchSeriesViewModel>()
        val state by viewModel.viewState.collectAsState()
        val pagingSeries = viewModel.pager.collectAsLazyPagingItems()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is SearchSeriesSideEffect.HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
                }

                is SearchSeriesSideEffect.GoToSeriesPreview -> {
                    focusManager.clearFocus()
                    GlobalNavigators.navigator?.push(SeriesPreviewScreenRoot(effect.series.id))
                }

                SearchSeriesSideEffect.HideLoaderWithSuccess -> {
                    loaderState.hideLoader()
                    snackbarState.showSuccess(Res.string.search_series_screen_add_success)
                }

                SearchSeriesSideEffect.ShowLoader ->  loaderState.showLoader()
            }
        }

        SearchSeriesScreen(
            pagingSeries = pagingSeries,
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
