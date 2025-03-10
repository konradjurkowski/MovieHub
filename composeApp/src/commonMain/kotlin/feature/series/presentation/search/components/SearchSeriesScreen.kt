package feature.series.presentation.search.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import core.components.media.PagingMediaList
import core.components.top_bar.SearchTopBar
import core.utils.clearFocus
import feature.series.presentation.search.SearchSeriesIntent.ClearQueryPressed
import feature.series.presentation.search.SearchSeriesIntent.SeriesAddPressed
import feature.series.presentation.search.SearchSeriesIntent.SeriesCardPressed
import feature.series.presentation.search.SearchSeriesIntent.QueryChanged
import feature.series.domain.model.Series
import feature.series.presentation.search.SearchSeriesIntent
import feature.series.presentation.search.SearchSeriesState

@Composable
fun SearchSeriesScreen(
    pagingSeries: LazyPagingItems<Series>,
    state: SearchSeriesState,
    onIntent: (SearchSeriesIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.clearFocus(),
        topBar = {
            SearchTopBar(
                value = state.query,
                onValueChange = { onIntent(QueryChanged(it)) },
                onClearPressed = { onIntent(ClearQueryPressed) },
            )
        },
    ) { contentPadding ->
        PagingMediaList(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            pagingMedia = pagingSeries,
            isMediaAdded = { series -> state.addedSeriesIds.contains(series.id) },
            onAddClick = { onIntent(SeriesAddPressed(it)) },
            onCardClick = { onIntent(SeriesCardPressed(it)) },
        )
    }
}
