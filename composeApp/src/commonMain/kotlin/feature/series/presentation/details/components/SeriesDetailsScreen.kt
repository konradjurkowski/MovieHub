package feature.series.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.media.details.MediaDetailsFailure
import core.components.media.details.MediaDetailsLoading
import feature.series.presentation.details.SeriesDetailsIntent
import feature.series.presentation.details.SeriesDetailsIntent.AddCommentPressed
import feature.series.presentation.details.SeriesDetailsIntent.BackPressed
import feature.series.presentation.details.SeriesDetailsIntent.DeleteCommentPressed
import feature.series.presentation.details.SeriesDetailsIntent.SetTab
import feature.series.presentation.details.SeriesDetailsIntent.Refresh
import feature.series.presentation.details.SeriesDetailsIntent.VideoPressed
import feature.series.presentation.details.SeriesDetailsState
import feature.series.presentation.details.SeriesDetailsState.Idle
import feature.series.presentation.details.SeriesDetailsState.Loading
import feature.series.presentation.details.SeriesDetailsState.Success
import feature.series.presentation.details.SeriesDetailsState.Error

@Composable
fun SeriesDetailsScreen(
    state: SeriesDetailsState,
    onIntent: (SeriesDetailsIntent) -> Unit,
) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                is Success -> {
                    SeriesDetailsSuccess(
                        series = state.series,
                        firebaseSeries = state.firebaseSeries,
                        castData = state.castData,
                        users = state.users,
                        selectedTab = state.selectedTab,
                        onBackPressed = { onIntent(BackPressed) },
                        onAddCommentPressed = { onIntent(AddCommentPressed(it)) },
                        onDeleteCommentPressed = {onIntent(DeleteCommentPressed(it))},
                        onTabPressed = { onIntent(SetTab(it)) },
                        onVideoPressed = { onIntent(VideoPressed(it)) },
                    )
                }
                Idle, Loading -> {
                    MediaDetailsLoading { onIntent(BackPressed) }
                }

                is Error -> {
                    MediaDetailsFailure(
                        onRefresh = { onIntent(Refresh) },
                        onBackPressed = { onIntent(BackPressed) },
                    )
                }
            }
        }
    }
}
