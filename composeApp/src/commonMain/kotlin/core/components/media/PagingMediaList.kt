package core.components.media

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import core.components.loading.LoadingIndicator
import core.components.result.EmptyView
import core.components.result.FailureWidget
import core.components.result.OngoingError
import core.components.result.OngoingLoading
import core.utils.Dimens
import core.utils.PagingState
import core.utils.getPagingState
import core.utils.isErrorAppend
import core.utils.isLoadingAppend
import feature.movies.domain.model.Movie
import feature.series.domain.model.Series
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.empty_search_message
import moviehub.composeapp.generated.resources.empty_search_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun <T : Any> PagingMediaList(
    modifier: Modifier = Modifier,
    pagingMedia: LazyPagingItems<T>,
    onClick: (T) -> Unit,
) {
    when (pagingMedia.getPagingState()) {
        PagingState.Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
        PagingState.Error -> FailureWidget { pagingMedia.retry() }

        PagingState.Empty -> {
            EmptyView(
                modifier = modifier,
                title = stringResource(Res.string.empty_search_title),
                message = stringResource(Res.string.empty_search_message),
            )
        }

        PagingState.Loaded -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .padding(top = Dimens.padding8)
                    .padding(horizontal = Dimens.padding16)
                    .fillMaxSize()
            ) {
                items(pagingMedia.itemCount) { index ->
                    pagingMedia[index]?.let { item ->
                        when (item) {
                            is Movie -> {
                                SearchMediaCard(
                                    title = item.title,
                                    imageUrl = item.posterPath,
                                    onClick = { onClick(item) },
                                )
                            }

                            is Series -> {
                                SearchMediaCard(
                                    title = item.name,
                                    imageUrl = item.posterPath,
                                    onClick = { onClick(item) },
                                )
                            }
                        }
                    }
                }

                if (pagingMedia.isLoadingAppend()) {
                    item(span = { GridItemSpan(maxLineSpan) }) { OngoingLoading() }
                }

                if (pagingMedia.isErrorAppend()) {
                    item(span = { GridItemSpan(maxLineSpan) }) { OngoingError { pagingMedia.retry() } }
                }
            }
        }
    }
}
