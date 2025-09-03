package com.konradjurkowski.moviehub.core.presentation.comp.media.paging

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import com.konradjurkowski.moviehub.core.presentation.comp.loading.LoadingIndicator
import com.konradjurkowski.moviehub.core.presentation.comp.result.EmptyView
import com.konradjurkowski.moviehub.core.presentation.comp.result.FailureWidget
import com.konradjurkowski.moviehub.core.presentation.comp.result.OngoingError
import com.konradjurkowski.moviehub.core.presentation.comp.result.OngoingLoading
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.extensions.PagingState
import com.konradjurkowski.moviehub.core.utils.extensions.getPagingState
import com.konradjurkowski.moviehub.core.utils.extensions.isErrorAppend
import com.konradjurkowski.moviehub.core.utils.extensions.isLoadingAppend
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.empty_search_message
import moviehub.composeapp.generated.resources.empty_search_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun <T : Any> GridMediaPagingList(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<T>,
    columns: GridCells = GridCells.Fixed(2),
    itemContent: @Composable (item: T) -> Unit,
) {
    when (pagingItems.getPagingState()) {
        PagingState.Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
        PagingState.Error -> FailureWidget { pagingItems.retry() }

        PagingState.Empty -> {
            EmptyView(
                modifier = modifier,
                title = stringResource(Res.string.empty_search_title),
                message = stringResource(Res.string.empty_search_message),
            )
        }

        PagingState.Loaded -> {
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = Dimens.padding8)
                    .padding(horizontal = Dimens.padding16),
                columns = columns,
            ) {
                items(count = pagingItems.itemCount) { index ->
                    val item = pagingItems[index] ?: return@items
                    itemContent(item)
                }

                if (pagingItems.isLoadingAppend()) {
                    item(span = { GridItemSpan(maxLineSpan) }) { OngoingLoading() }
                }

                if (pagingItems.isErrorAppend()) {
                    item(span = { GridItemSpan(maxLineSpan) }) { OngoingError { pagingItems.retry() } }
                }
            }
        }
    }
}
