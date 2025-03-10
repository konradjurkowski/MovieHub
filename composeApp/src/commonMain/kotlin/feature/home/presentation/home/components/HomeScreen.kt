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
import feature.home.presentation.home.HomeIntent.MoviePressed
import feature.home.presentation.home.HomeIntent.OnDrawMoviePressed
import feature.home.presentation.home.HomeIntent.OnDrawSeriesPressed
import feature.home.presentation.home.HomeIntent.OnUserPressed
import feature.home.presentation.home.HomeIntent.SeriesPressed
import feature.home.presentation.home.HomeIntent.TryAgainPressed
import feature.home.presentation.home.HomeState.Idle
import feature.home.presentation.home.HomeState.Loading
import feature.home.presentation.home.HomeState.Success
import feature.home.presentation.home.HomeState.Error
import feature.home.presentation.home.HomeState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.home_screen_recently_updated_movies
import moviehub.composeapp.generated.resources.home_screen_recently_updated_series
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    when (state) {
        is Success -> {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = innerPadding.calculateBottomPadding())
                        .verticalScroll(rememberScrollState()),
                ) {
                    HomeHeader(
                        appUser = state.appUser,
                        onUserClick = { onIntent(OnUserPressed) },
                    )
                    HomeDrawMediaSection(
                        movies = state.firebaseMovies,
                        series = state.firebaseSeries,
                        onDrawMoviePressed = { onIntent(OnDrawMoviePressed) },
                        onDrawSeriesPressed = { onIntent(OnDrawSeriesPressed) },
                    )
                    MediaCarouselWithTitle(
                        title = stringResource(Res.string.home_screen_recently_updated_movies),
                        items = state.lastUpdatedMovies,
                        onItemClick = { onIntent(MoviePressed(it)) },
                    )
                    RegularSpacer()
                    MediaCarouselWithTitle(
                        title = stringResource(Res.string.home_screen_recently_updated_series),
                        items = state.lastUpdatedSeries,
                        onItemClick = { onIntent(SeriesPressed(it)) },
                    )
                    RegularSpacer()
                }
            }
        }

        is Idle, is Loading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }

        is Error -> {
            FailureWidget { onIntent(TryAgainPressed) }
        }
    }
}
