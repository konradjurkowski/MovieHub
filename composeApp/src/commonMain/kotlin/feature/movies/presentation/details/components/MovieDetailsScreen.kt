package feature.movies.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.media.details.MediaDetailsFailure
import core.components.media.details.MediaDetailsLoading
import feature.movies.presentation.details.MovieDetailsIntent
import feature.movies.presentation.details.MovieDetailsState

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onIntent: (MovieDetailsIntent) -> Unit,
) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> MediaDetailsLoading { onIntent(MovieDetailsIntent.BackPressed) }

                !state.isLoading && state.movie != null && state.firebaseMovie != null && state.castData != null -> {
                    MovieDetailsSuccess(
                        movie = state.movie,
                        firebaseMovie = state.firebaseMovie,
                        castData = state.castData,
                        selectedTab = state.selectedTab,
                        users = state.users,
                        onBackPressed = { onIntent(MovieDetailsIntent.BackPressed) },
                        onAddCommentPressed = { onIntent(MovieDetailsIntent.AddCommentPressed(it)) },
                        onDeleteCommentPressed = { onIntent(MovieDetailsIntent.DeleteCommentPressed(it)) },
                        onTabPressed = { onIntent(MovieDetailsIntent.SetTab(it)) },
                    )
                }

                else -> {
                    MediaDetailsFailure(
                        onBackPressed = { onIntent(MovieDetailsIntent.BackPressed) },
                        onRefresh = { onIntent(MovieDetailsIntent.Refresh) },
                    )
                }
            }
        }
    }
}
