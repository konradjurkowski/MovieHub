package feature.home.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.components.loading.LoadingIndicator
import core.components.other.RegularSpacer
import core.components.result.FailureWidget
import core.navigation.GlobalNavigators
import core.utils.Dimens
import feature.home.presentation.draw.DrawMediaScreenRoot
import feature.home.presentation.draw.DrawType
import feature.home.presentation.home.HomeIntent
import feature.home.presentation.home.HomeState
import feature.home.presentation.home.isDataLoaded

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    when {
        state.isLoading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }

        state.isDataLoaded()  -> {
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
                    Row(modifier = Modifier.padding(horizontal = Dimens.padding16)) {
                        // TODO
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .background(Color.Red)
                                .clickable {
                                    GlobalNavigators.navigator?.push(DrawMediaScreenRoot(drawType = DrawType.MOVIE))
                                }
                        )
                        RegularSpacer()
                        // TODO
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .background(Color.Green)
                                .clickable {
                                    GlobalNavigators.navigator?.push(DrawMediaScreenRoot(drawType = DrawType.SERIES))
                                }
                        )
                    }
                    RegularSpacer()
                    MediaCarouselWithTitle(
                        title = "Recently updated movies",
                        items = state.lastUpdatedMovies ?: emptyList(),
                        onItemClick = { onIntent(HomeIntent.MoviePressed(it)) },
                    )
                    RegularSpacer()
                    MediaCarouselWithTitle(
                        title = "Recently updated series",
                        items = state.lastUpdatedSeries ?: emptyList(),
                        onItemClick = { onIntent(HomeIntent.SeriesPressed(it)) },
                    )
                    RegularSpacer()
                }
            }
        }

        else -> FailureWidget {  }
    }
}
