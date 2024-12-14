package feature.add.presentation.add

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.CollectSideEffects
import feature.add.presentation.add.components.AddScreen
import feature.movies.presentation.search.SearchMovieScreenRoot
import feature.series.presentation.search.SearchSeriesScreenRoot

class AddScreenRoot : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<AddScreenViewModel>()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                AddScreenSideEffect.GoToAddMovie -> navigator.push(SearchMovieScreenRoot())
                AddScreenSideEffect.GoToAddSeries -> navigator.push(SearchSeriesScreenRoot())
            }
        }

        AddScreen(onIntent = viewModel::sendIntent)
    }
}
