package feature.home.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.loading.LoadingIndicator
import core.components.media.MediaCarousel
import core.components.other.RegularSpacer
import core.components.result.FailureWidget
import core.utils.Dimens
import feature.home.presentation.home.HomeIntent
import feature.home.presentation.home.HomeState

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit
) {
    when {
        state.isLoading && state.user == null && state.lastUpdatedMovies.isEmpty() -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }
        !state.isLoading && state.user != null -> {
            Scaffold(
                topBar = {
                    HomeHeader(state.user)
                }
            ) { contentPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = Dimens.padding16),
                        text = "Ostatnio zaktualizowane filmy"
                    )
                    RegularSpacer()
                    MediaCarousel(items = state.lastUpdatedMovies)
                }
            }
        }
        else -> {
            FailureWidget {  }
        }
    }
}
