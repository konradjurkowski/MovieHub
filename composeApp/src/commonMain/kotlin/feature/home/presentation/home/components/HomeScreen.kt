package feature.home.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.loading.LoadingIndicator
import core.components.other.RegularSpacer
import core.components.result.FailureWidget
import feature.home.presentation.home.HomeIntent
import feature.home.presentation.home.HomeState
import feature.home.presentation.home.isDataLoaded
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.home_screen_recently_updated_movies
import moviehub.composeapp.generated.resources.home_screen_recently_updated_series
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    when {
        state.isLoading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }

        state.isDataLoaded() -> {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    HomeHeader(
                        appUser = state.appUser,
                        onUserClick = {
                            onIntent(HomeIntent.OnUserPressed)
                        },
                    )
                    HomeDrawMediaSection(
                        movies = state.firebaseMovies,
                        series = state.firebaseSeries,
                        onDrawMoviePressed = { onIntent(HomeIntent.OnDrawMoviePressed) },
                        onDrawSeriesPressed = { onIntent(HomeIntent.OnDrawSeriesPressed) },
                    )
                    MediaCarouselWithTitle(
                        title = stringResource(Res.string.home_screen_recently_updated_movies),
                        items = state.lastUpdatedMovies ?: emptyList(),
                        onItemClick = { onIntent(HomeIntent.MoviePressed(it)) },
                    )
                    RegularSpacer()
                    MediaCarouselWithTitle(
                        title = stringResource(Res.string.home_screen_recently_updated_series),
                        items = state.lastUpdatedSeries ?: emptyList(),
                        onItemClick = { onIntent(HomeIntent.SeriesPressed(it)) },
                    )
                    RegularSpacer()
                }
            }
        }

        else -> FailureWidget { onIntent(HomeIntent.TryAgainPressed) }
    }
}
