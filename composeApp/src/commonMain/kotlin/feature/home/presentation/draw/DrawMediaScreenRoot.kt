package feature.home.presentation.draw

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import feature.home.presentation.draw.components.DrawMediaScreen
import feature.movies.presentation.details.MovieDetailsScreenRoot
import feature.series.presentation.details.SeriesDetailsScreenRoot
import org.koin.core.parameter.parametersOf

enum class DrawType { MOVIE, SERIES }

class DrawMediaScreenRoot(private val drawType: DrawType) : BaseScreen() {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<DrawMediaViewModel> { parametersOf(drawType) }
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is DrawMediaSideEffect.GoToMovieDetails -> {
                    GlobalNavigators.navigator?.pop()
                    GlobalNavigators.navigator?.push(MovieDetailsScreenRoot(effect.movie.movieId))
                }

                is DrawMediaSideEffect.GoToSeriesDetails -> {
                    GlobalNavigators.navigator?.pop()
                    GlobalNavigators.navigator?.push(SeriesDetailsScreenRoot(effect.series.seriesId))
                }
            }
        }

        DrawMediaScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
