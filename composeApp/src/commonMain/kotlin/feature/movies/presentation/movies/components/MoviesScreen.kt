package feature.movies.presentation.movies.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.loading.LoadingIndicator
import core.components.media.LeaderboardMediaCard
import core.components.result.EmptyView
import core.components.result.FailureWidget
import core.components.top_bar.LogoTopBar
import core.utils.Dimens
import core.utils.Resource
import feature.movies.presentation.movies.MoviesIntent
import feature.movies.presentation.movies.MoviesState

@Composable
fun MoviesScreen(
    state: MoviesState,
    onIntent: (MoviesIntent) -> Unit,
) {
    Scaffold(
        topBar = { LogoTopBar() },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            when (state) {
                Resource.Idle, Resource.Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
                is Resource.Failure -> FailureWidget { onIntent(MoviesIntent.Refresh) }

                is Resource.Success -> {
                    if (state.data.isEmpty()) return@Scaffold EmptyView()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = Dimens.padding8)
                            .padding(horizontal = Dimens.padding16),
                    ) {
                        itemsIndexed(state.data) { index, movie ->
                            LeaderboardMediaCard(
                                modifier = Modifier.padding(bottom = Dimens.padding16),
                                title = movie.title,
                                imageUrl = movie.posterPath,
                                position = index + 1,
                                rating = movie.averageRating,
                                onClick = { onIntent(MoviesIntent.MoviePressed(movie)) },
                            )
                        }
                    }
                }
            }
        }
    }
}
