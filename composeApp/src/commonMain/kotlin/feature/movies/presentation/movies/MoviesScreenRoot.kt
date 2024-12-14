package feature.movies.presentation.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import feature.movies.presentation.details.MovieDetailsScreenRoot
import feature.movies.presentation.movies.components.MoviesScreen

class MoviesScreenRoot : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MoviesViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is MoviesSideEffect.GoToMovieDetail -> {
                    GlobalNavigators.navigator?.push(MovieDetailsScreenRoot(effect.movie.movieId))
                }
            }
        }

        MoviesScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
