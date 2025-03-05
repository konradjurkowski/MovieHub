package feature.series.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.media.details.MediaDetailsFailure
import core.components.media.details.MediaDetailsLoading
import feature.series.presentation.details.SeriesDetailsIntent
import feature.series.presentation.details.SeriesDetailsState

@Composable
fun SeriesDetailsScreen(
    state: SeriesDetailsState,
    onIntent: (SeriesDetailsIntent) -> Unit,
) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                is SeriesDetailsState.Success -> {
                    SeriesDetailsSuccess(
                        series = state.series,
                        firebaseSeries = state.firebaseSeries,
                        castData = state.castData,
                        users = state.users,
                        selectedTab = state.selectedTab,
                        onBackPressed = { onIntent(SeriesDetailsIntent.BackPressed) },
                        onAddCommentPressed = { onIntent(SeriesDetailsIntent.AddCommentPressed(it)) },
                        onDeleteCommentPressed = {onIntent(SeriesDetailsIntent.DeleteCommentPressed(it))},
                        onTabPressed = { onIntent(SeriesDetailsIntent.SetTab(it)) },
                    )
                }
                SeriesDetailsState.Idle, SeriesDetailsState.Loading -> {
                    MediaDetailsLoading { onIntent(SeriesDetailsIntent.BackPressed) }
                }

                is SeriesDetailsState.Error -> {
                    MediaDetailsFailure(
                        onRefresh = { onIntent(SeriesDetailsIntent.Refresh) },
                        onBackPressed = { onIntent(SeriesDetailsIntent.BackPressed) },
                    )
                }
            }
        }
    }
}
