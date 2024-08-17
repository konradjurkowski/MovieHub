package feature.series.presentation.series.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.loading.LoadingIndicator
import core.components.media.LeaderboardMediaCard
import core.components.result.EmptyView
import core.components.result.FailureWidget
import core.components.top_bar.LogoTopBar
import core.utils.Dimens
import core.utils.Resource
import feature.series.presentation.series.SeriesIntent
import feature.series.presentation.series.SeriesState

@Composable
fun SeriesScreen(
    state: SeriesState,
    onIntent: (SeriesIntent) -> Unit,
) {
    Scaffold(
        topBar = { LogoTopBar() },
    ) { contentPadding ->
        when (state) {
            is Resource.Success -> {
                if (state.data.isEmpty()) return@Scaffold EmptyView()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(top = Dimens.padding8)
                        .padding(horizontal = Dimens.padding16),
                ) {
                    itemsIndexed(state.data) { index, series ->
                        LeaderboardMediaCard(
                            modifier = Modifier.padding(bottom = Dimens.padding16),
                            title = series.name,
                            imageUrl = series.posterPath,
                            position = index + 1,
                            rating = series.averageRating,
                            onClick = { onIntent(SeriesIntent.SeriesPressed(series)) },
                        )
                    }
                }
            }

            Resource.Idle, Resource.Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())

            is Resource.Failure -> {
                FailureWidget { onIntent(SeriesIntent.Refresh) }
            }
        }
    }
}
