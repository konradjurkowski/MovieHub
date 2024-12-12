package feature.add.presentation.search_series

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
import feature.add.presentation.search_series.components.SearchSeriesScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.search_series_screen_add_success
import kotlin.random.Random

class SearchSeriesScreenRoot : Screen {

    override val key: ScreenKey = super.key + Random.nextDouble()

    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current
        val loaderState = LocalLoaderState.current

        val viewModel = getScreenModel<SearchSeriesViewModel>()
        val state by viewModel.viewState.collectAsState()
        val pagingSeries = viewModel.pager.collectAsLazyPagingItems()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is SearchSeriesSideEffect.HideLoaderWithError -> {
                    loaderState.hideLoader()
                    snackbarState.showError(getFailureMessage(effect.error))
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
