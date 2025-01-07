package feature.home.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.loading.LoadingIndicator
import core.components.media.MediaCarousel
import core.components.other.RegularSpacer
import core.components.result.FailureWidget
import core.components.text.SectionTitle
import core.utils.Dimens
import feature.home.presentation.home.HomeIntent
import feature.home.presentation.home.HomeState

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    when {
        state.isLoading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }

        !state.isLoading && state.lastUpdatedMovies != null -> {
            Scaffold(
                topBar = {
                    HomeHeader(
                        appUser = state.appUser,
                        onUserClick = {
                            onIntent(HomeIntent.OnUserPressed)
                        },
                    )
                },
            ) { contentPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                ) {
                    SectionTitle(
                        modifier = Modifier.padding(horizontal = Dimens.padding16),
                        title = "Recently updated videos",
                    )
                    RegularSpacer()
                    // TODO handle empty list
                    MediaCarousel(
                        items = state.lastUpdatedMovies,
                        onItemClick = { onIntent(HomeIntent.MoviePressed(it)) },
                    )
                }
                // TODO HORIZONTAL LIST OF DIFFERENT TYPE OF SERIES AND MOVIES AND SHAKOMAT
            }
        }

        else -> {
            FailureWidget {  }
        }
    }
}
