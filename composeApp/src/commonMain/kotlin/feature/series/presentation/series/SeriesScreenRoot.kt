package feature.series.presentation.series

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import core.utils.safePush
import feature.series.presentation.series.components.SeriesScreen
import feature.series.presentation.details.SeriesDetailsScreenRoot
import feature.series.presentation.search.SearchSeriesScreenRoot

class SeriesScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<SeriesViewModel>()
        val state by viewModel.viewState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getSeries()
        }

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is SeriesSideEffect.GoToSeriesDetail -> {
                    GlobalNavigators.navigator?.safePush(SeriesDetailsScreenRoot(effect.series.seriesId))
                }

                is SeriesSideEffect.GoToAddSeries -> {
                    GlobalNavigators.navigator?.safePush(SearchSeriesScreenRoot())
                }
            }
        }

        SeriesScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
