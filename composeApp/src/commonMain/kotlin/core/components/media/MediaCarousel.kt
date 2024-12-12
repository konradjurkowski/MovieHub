package core.components.media

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import core.components.image.AnyImage
import core.utils.Dimens
import core.utils.LocalTouchFeedback
import core.utils.getScreenSizeInfo
import feature.movies.domain.model.FirebaseMovie
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlin.math.absoluteValue

@Composable
fun MediaCarousel(
    modifier: Modifier = Modifier,
    items: List<FirebaseMovie>,
) {
    if (items.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = items.size / 2,
        pageCount = { items.size },
    )

    val touchFeedback = LocalTouchFeedback.current
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .drop(1)
            .distinctUntilChanged()
            .collect {
                touchFeedback.performLongPress()
            }
    }

    val screeSize = getScreenSizeInfo()

    HorizontalPager(
        modifier = modifier
            .height(screeSize.height * 0.35f),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = screeSize.width * 0.25f)
    ) { index ->
        val item = items[index]

        Card(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset =
                        (pagerState.currentPage - index + pagerState.currentPageOffsetFraction).absoluteValue

                    lerp(
                        start = 0.8f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.3f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                },
            shape = RoundedCornerShape(Dimens.radius12),
        ) {
            AnyImage(
                modifier = Modifier.fillMaxSize(),
                image = item.posterPath,
            )
        }
    }
}
