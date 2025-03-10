package feature.series.presentation.series.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.button.AddButton
import core.components.loading.LoadingIndicator
import core.components.media.LeaderboardMediaCard
import core.components.result.EmptyView
import core.components.result.FailureWidget
import core.components.top_bar.MainTopBar
import core.utils.Dimens
import feature.series.presentation.series.SeriesIntent
import feature.series.presentation.series.SeriesIntent.AddSeriesPressed
import feature.series.presentation.series.SeriesIntent.SeriesPressed
import feature.series.presentation.series.SeriesIntent.Refresh
import feature.series.presentation.series.SeriesState
import feature.series.presentation.series.SeriesState.Idle
import feature.series.presentation.series.SeriesState.Loading
import feature.series.presentation.series.SeriesState.Success
import feature.series.presentation.series.SeriesState.Error
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.series_tab_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SeriesScreen(
    state: SeriesState,
    onIntent: (SeriesIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            MainTopBar(
                title = stringResource(Res.string.series_tab_label),
                isLeadingVisible = false,
                actions = {
                    AddButton(
                        modifier = Modifier.padding(horizontal = Dimens.padding16),
                        onClick = { onIntent(AddSeriesPressed) },
                    )
                },
            )
        },
    ) { contentPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        ) {
            when (state) {
                is Success -> {
                    if (state.series.isEmpty()) return@Scaffold EmptyView()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = Dimens.padding8)
                            .padding(horizontal = Dimens.padding16),
                    ) {
                        itemsIndexed(state.series) { index, series ->
                            LeaderboardMediaCard(
                                modifier = Modifier.padding(bottom = Dimens.padding16),
                                title = series.name,
                                imageUrl = series.posterPath,
                                position = index + 1,
                                rating = series.averageRating,
                                onClick = { onIntent(SeriesPressed(series)) },
                            )
                        }
                    }
                }

                Idle, Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
                is Error -> FailureWidget { onIntent(Refresh) }
            }
        }
    }
}
