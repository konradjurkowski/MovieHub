package feature.series.presentation.series_details.components

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
import feature.series.presentation.series_details.SeriesDetailsIntent
import feature.series.presentation.series_details.SeriesDetailsState

@Composable
fun SeriesDetailsScreen(
    state: SeriesDetailsState,
    onIntent: (SeriesDetailsIntent) -> Unit,
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
                            onClick = { onIntent(SeriesDetailsIntent.BackPressed) },
                        )
                        LoadingIndicator(modifier = Modifier.fillMaxSize().weight(1f))
                    }
                }

                !state.isLoading && state.series != null && state.firebaseSeries != null && state.castData != null -> {
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

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimens.padding16),
                    ) {
                        BoxButton(
                            modifier = Modifier.safeDrawingPadding(),
                            onClick = { onIntent(SeriesDetailsIntent.BackPressed) },
                        )
                        FailureWidget(
                            modifier = Modifier.weight(1f),
                            onButtonClick = { onIntent(SeriesDetailsIntent.Refresh) },
                        )
                    }
                }
            }
        }
    }
}
