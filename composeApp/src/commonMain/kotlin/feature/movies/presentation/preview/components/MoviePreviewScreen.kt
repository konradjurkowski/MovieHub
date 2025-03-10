package feature.movies.presentation.preview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.button.AddMediaButton
import core.components.media.details.MediaDetailsFailure
import core.components.media.details.MediaDetailsLoading
import feature.movies.presentation.preview.MoviePreviewIntent
import feature.movies.presentation.preview.MoviePreviewState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.movie_screen_preview_add_movie
import moviehub.composeapp.generated.resources.movie_screen_preview_movie_added
import org.jetbrains.compose.resources.stringResource

@Composable
fun MoviePreviewScreen(
    state: MoviePreviewState,
    onIntent: (MoviePreviewIntent) -> Unit,
) {
    Scaffold(
        bottomBar = {
            AddMediaButton(
                addedTitle = stringResource(Res.string.movie_screen_preview_movie_added),
                notAddedTitle = stringResource(Res.string.movie_screen_preview_add_movie),
                isVisible = state.isSuccess(),
                isAdded = state.isMediaAdded(),
                onClick = {
                    val movie = state.getSuccess()?.movie
                    if (movie != null) onIntent(MoviePreviewIntent.MovieAddPressed(movie))
                },
            )
        },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(bottom = contentPadding.calculateBottomPadding())
                .fillMaxSize(),
        ) {
            when (state) {
                is MoviePreviewState.Success -> {
                    MoviePreviewSuccess(
                        movie = state.movie,
                        castData = state.castData,
                        onBackPressed = { onIntent(MoviePreviewIntent.BackPressed) },
                        onVideoPressed = { onIntent(MoviePreviewIntent.VideoPressed(it)) },
                    )
                }

                MoviePreviewState.Idle, MoviePreviewState.Loading -> MediaDetailsLoading { onIntent(MoviePreviewIntent.BackPressed) }

                is MoviePreviewState.Error -> {
                    MediaDetailsFailure(
                        onBackPressed = { onIntent(MoviePreviewIntent.BackPressed) },
                        onRefresh = { onIntent(MoviePreviewIntent.Refresh) },
                    )
                }
            }
        }
    }
}
