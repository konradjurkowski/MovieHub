package feature.add.presentation.search_movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.CollectSideEffects
import feature.add.presentation.search_movie.components.SearchMovieScreen

class SearchMovieScreenRoot : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<SearchMovieViewModel>()
        val state by viewModel.viewState.collectAsState()
        val pagingMovies = viewModel.pager.collectAsLazyPagingItems()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->

        }

        SearchMovieScreen(
            pagingMovies = pagingMovies,
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
