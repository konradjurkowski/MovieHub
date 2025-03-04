package feature.home.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import core.utils.safePush
import feature.home.presentation.draw.DrawMediaScreenRoot
import feature.home.presentation.home.components.HomeScreen
import feature.movies.presentation.details.MovieDetailsScreenRoot
import feature.profile.presentation.tab.ProfileTab
import feature.series.presentation.details.SeriesDetailsScreenRoot

class HomeScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is HomeSideEffect.GoToMovieDetails -> {
                    GlobalNavigators.navigator?.safePush(MovieDetailsScreenRoot(effect.movie.movieId))
                }

                is HomeSideEffect.GoToSeriesDetails -> {
                    GlobalNavigators.navigator?.safePush(SeriesDetailsScreenRoot(effect.series.seriesId))
                }

                is HomeSideEffect.GoToDrawMedia -> {
                    GlobalNavigators.navigator?.safePush(DrawMediaScreenRoot(effect.drawType))
                }

                HomeSideEffect.GoToProfileTab -> {
                    GlobalNavigators.tabNavigator?.current = ProfileTab
                }
            }
        }

        HomeScreen(
            state = state,
            onIntent = viewModel::sendIntent
        )
    }
}
