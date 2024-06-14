package core.components.media

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.utils.Dimens
import feature.movies.domain.model.Movie
import feature.series.domain.model.Series

@Composable
fun <T> HorizontalMediaList(
    mediaList: List<T> = emptyList(),
    isLoading: Boolean = false,
) {
    if (!isLoading) {
        LazyRow {
            itemsIndexed(mediaList) { index, item ->
                if (item is Movie) {
                    HorizontalMediaCard(
                        modifier = Modifier
                            .padding(
                                start = if (index == 0) Dimens.regularPadding else 0.dp,
                                end = Dimens.regularPadding
                            ),
                        title = item.title,
                        imageUrl = item.posterPath ?: ""
                    )
                }
                if (item is Series) {
                    HorizontalMediaCard(
                        modifier = Modifier
                            .padding(
                                start = if (index == 0) Dimens.regularPadding else 0.dp,
                                end = Dimens.regularPadding
                            ),
                        title = item.name,
                        imageUrl = item.posterPath
                    )
                }
            }
        }
    } else {
        LazyRow {
            items(20) { index ->
                HorizontalMediaCardPlaceholder(
                    modifier = Modifier
                        .padding(
                            start = if (index == 0) Dimens.regularPadding else 0.dp,
                            end = Dimens.regularPadding
                        )
                )
            }
        }
    }
}
