package feature.movies.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.media.details.MediaDetailsFailure
import core.components.media.details.MediaDetailsLoading
import feature.movies.presentation.details.MovieDetailsIntent
import feature.movies.presentation.details.MovieDetailsIntent.AddCommentPressed
import feature.movies.presentation.details.MovieDetailsIntent.BackPressed
import feature.movies.presentation.details.MovieDetailsIntent.DeleteCommentPressed
import feature.movies.presentation.details.MovieDetailsIntent.Refresh
import feature.movies.presentation.details.MovieDetailsIntent.SetTab
import feature.movies.presentation.details.MovieDetailsIntent.VideoPressed
import feature.movies.presentation.details.MovieDetailsState
import feature.movies.presentation.details.MovieDetailsState.Idle
import feature.movies.presentation.details.MovieDetailsState.Loading
import feature.movies.presentation.details.MovieDetailsState.Success
import feature.movies.presentation.details.MovieDetailsState.Error

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onIntent: (MovieDetailsIntent) -> Unit,
) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                is Success -> {
                    MovieDetailsSuccess(
                        movie = state.movie,
                        firebaseMovie = state.firebaseMovie,
                        castData = state.castData,
                        selectedTab = state.selectedTab,
                        users = state.users,
                        onBackPressed = { onIntent(BackPressed) },
                        onAddCommentPressed = { onIntent(AddCommentPressed(it)) },
                        onDeleteCommentPressed = { onIntent(DeleteCommentPressed(it)) },
                        onTabPressed = { onIntent(SetTab(it)) },
                        onVideoPressed = { onIntent(VideoPressed(it)) },
                    )
                }

                Idle, Loading -> {
                    MediaDetailsLoading { onIntent(BackPressed) }
                }

                is Error -> {
                    MediaDetailsFailure(
                        onBackPressed = { onIntent(BackPressed) },
                        onRefresh = { onIntent(Refresh) },
                    )
                }
            }
        }
    }
}
