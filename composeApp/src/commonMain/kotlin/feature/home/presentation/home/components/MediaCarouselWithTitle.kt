package feature.home.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.media.MediaCarousel
import core.components.other.RegularSpacer
import core.components.text.SectionTitle
import core.utils.Dimens

@Composable
fun <T> MediaCarouselWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    items: List<T>,
    onItemClick: (T) -> Unit = {},
) {
    Column(modifier = modifier) {
        SectionTitle(
            modifier = Modifier.padding(horizontal = Dimens.padding16),
            title = title,
        )
        RegularSpacer()
        MediaCarousel(
            items = items,
            onItemClick = onItemClick,
        )
    }
}
