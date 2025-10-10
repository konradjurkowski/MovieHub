package com.konradjurkowski.moviehub.feature.movies.presentation.preview.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.core.presentation.comp.button.FooterButton
import com.konradjurkowski.moviehub.core.presentation.comp.media.details.MediaDetailsError
import com.konradjurkowski.moviehub.core.presentation.comp.media.details.MediaDetailsLoading
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.BackPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.MovieAddPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.VideoPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.Refresh
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Error
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Idle
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Loading
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Success
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.movie_screen_preview_add_movie
import moviehub.composeapp.generated.resources.movie_screen_preview_movie_added
import org.jetbrains.compose.resources.stringResource

@Composable
fun MoviePreviewContent(
    state: MoviePreviewState,
    onIntent: (MoviePreviewIntent) -> Unit,
) {
    Scaffold(
        bottomBar = {
            val text = when (state.isMovieAdded()) {
                true -> stringResource(Res.string.movie_screen_preview_movie_added)
                false -> stringResource(Res.string.movie_screen_preview_add_movie)
            }
            val icon = Icons.Default.Add.takeUnless { state.isMovieAdded() }

            FooterButton(
                text = text,
                icon = icon,
                visible = state.isDataLoaded(),
                enabled = !state.isMovieAdded(),
                onClick = {
                    val movie = state.getSuccess()?.movie ?: return@FooterButton
                    onIntent(MovieAddPressed(movie))
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
        ) {
            when (state) {
                Idle, Loading -> MediaDetailsLoading { onIntent(BackPressed) }

                is Error -> MediaDetailsError(
                    onBackClick = { onIntent(BackPressed) },
                    onRefreshClick = { onIntent(Refresh) },
                )

                is Success -> MoviePreviewSuccess(
                    movie = state.movie,
                    onBackClick = { onIntent(BackPressed) },
                    onVideoClick = { onIntent(VideoPressed(it)) },
                )
            }
        }
    }
}
