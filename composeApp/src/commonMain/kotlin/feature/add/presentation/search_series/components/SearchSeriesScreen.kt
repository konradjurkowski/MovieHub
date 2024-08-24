package feature.add.presentation.search_series.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import app.cash.paging.compose.LazyPagingItems
import core.components.media.PagingMediaList
import core.components.top_bar.SearchTopBar
import core.utils.clearFocus
import feature.add.presentation.search_series.SearchSeriesIntent
import feature.add.presentation.search_series.SearchSeriesState
import feature.series.domain.model.Series

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
                shouldRequestFocus = !state.isSearchInitiated,
                onValueChange = { onIntent(SearchSeriesIntent.QueryChanged(it)) },
                onClearPressed = { onIntent(SearchSeriesIntent.ClearQueryPressed) },
            )
        },
    ) { contentPadding ->
        if (state.isSearchInitiated) {
            PagingMediaList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                pagingMedia = pagingSeries,
                onClick = { onIntent(SearchSeriesIntent.SeriesPressed(it)) },
            )
        }
    }
}
