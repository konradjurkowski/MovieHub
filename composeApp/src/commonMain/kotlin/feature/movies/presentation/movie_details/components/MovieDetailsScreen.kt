package feature.movies.presentation.movie_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.button.BoxButton
import core.components.loading.LoadingIndicator
import core.components.result.FailureWidget
import core.utils.Dimens
import feature.movies.presentation.movie_details.MovieDetailsIntent
import feature.movies.presentation.movie_details.MovieDetailsState

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onIntent: (MovieDetailsIntent) -> Unit,
) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimens.padding16),
                    ) {
                        BoxButton(
                            modifier = Modifier.safeDrawingPadding(),
                            onClick = { onIntent(MovieDetailsIntent.BackPressed) },
                        )
                        LoadingIndicator(modifier = Modifier.fillMaxSize().weight(1f))
                    }
                }

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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimens.padding16),
                    ) {
                        BoxButton(
                            modifier = Modifier.safeDrawingPadding(),
                            onClick = { onIntent(MovieDetailsIntent.BackPressed) },
                        )
                        FailureWidget(
                            modifier = Modifier.weight(1f),
                            onButtonClick = { onIntent(MovieDetailsIntent.Refresh) },
                        )
                    }
                }
            }
        }
    }
}
