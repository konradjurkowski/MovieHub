package core.components.media.cast

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.utils.paddingForIndex
import feature.movies.domain.model.Cast

@Composable
fun CastHorizontalList(
    modifier: Modifier = Modifier,
    castList: List<Cast>,
) {
    LazyRow(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth(),
    ) {
        itemsIndexed(castList) { index, cast ->
            CastCard(
                modifier = Modifier.paddingForIndex(index = index, size = castList.size),
                cast = cast,
            )
        }
    }
}
