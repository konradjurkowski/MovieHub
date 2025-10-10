package com.konradjurkowski.moviehub.feature.movies.presentation.movies.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.core.presentation.comp.button.AnimatedIconButton
import com.konradjurkowski.moviehub.core.presentation.comp.loading.LoadingIndicator
import com.konradjurkowski.moviehub.core.presentation.comp.media.leaderboard.LeaderboardMediaCard
import com.konradjurkowski.moviehub.core.presentation.comp.result.EmptyView
import com.konradjurkowski.moviehub.core.presentation.comp.result.FailureWidget
import com.konradjurkowski.moviehub.core.presentation.comp.top_bar.MainTopBar
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesIntent
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesIntent.AddMovieClick
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesIntent.MovieClick
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesIntent.Refresh
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesState
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesState.Error
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesState.Idle
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesState.Loading
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesState.Success
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.movies_tab_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun MoviesContent(
    state: MoviesState,
    onIntent: (MoviesIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            MainTopBar(
                title = stringResource(Res.string.movies_tab_label),
                actions = {
                    AnimatedIconButton(
                        modifier = Modifier.padding(horizontal = Dimens.padding16),
                        icon = Icons.Default.Add,
                        onClick = { onIntent(AddMovieClick) },
                    )
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (state) {
                Idle, Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
                is Error -> FailureWidget { onIntent(Refresh) }

                is Success -> {
                    if (state.movies.isEmpty()) return@Scaffold EmptyView()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimens.padding16),
                    ) {
                        itemsIndexed(state.movies) { index, movie ->
                            LeaderboardMediaCard(
                                modifier = Modifier.padding(bottom = Dimens.padding16),
                                title = movie.title,
                                imageUrl = movie.posterUrl,
                                position = index + 1,
                                rating = 0.0,
                                onClick = { onIntent(MovieClick(movie)) },
                            )
                        }
                        item {
                            // TODO SHOW MORE BUTTON
                        }
                    }
                }
            }
        }
    }
}
