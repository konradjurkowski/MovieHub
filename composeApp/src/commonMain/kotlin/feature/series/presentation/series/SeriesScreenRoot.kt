package feature.series.presentation.series

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import feature.series.presentation.series.components.SeriesScreen
import feature.series.presentation.details.SeriesDetailsScreenRoot
import feature.series.presentation.search.SearchSeriesScreenRoot

class SeriesScreenRoot : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<SeriesViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is SeriesSideEffect.GoToSeriesDetail -> {
                    GlobalNavigators.navigator?.push(SeriesDetailsScreenRoot(effect.series.seriesId))
                }

                is SeriesSideEffect.GoToAddSeries -> {
                    GlobalNavigators.navigator?.push(SearchSeriesScreenRoot())
                }
            }
        }

        SeriesScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
