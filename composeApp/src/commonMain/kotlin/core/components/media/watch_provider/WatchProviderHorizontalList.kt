package core.components.media.watch_provider

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.model.media.WatchProviderInfo
import core.utils.paddingForIndex

@Composable
fun WatchProviderHorizontalList(
    modifier: Modifier = Modifier,
    watchProviderList: List<WatchProviderInfo>,
) {
    LazyRow(
        modifier = modifier
            .height(75.dp)
            .fillMaxWidth(),
    ) {
        itemsIndexed(watchProviderList) { index, provider ->
            WatchProviderCard(
                modifier = Modifier.paddingForIndex(index = index, size = watchProviderList.size),
                provider = provider,
            )
        }
    }
}
