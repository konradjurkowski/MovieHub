package core.components.media.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.components.image.AnyImage
import core.model.media.Video
import core.model.media.getThumbnailUrl
import core.theme.withA40
import core.utils.Dimens
import core.utils.LocalTouchFeedback

@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    video: Video,
    onPressed: (Video) -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current

    Card(
        modifier = modifier
            .height(150.dp)
            .aspectRatio(16/9f),
        shape = RoundedCornerShape(Dimens.radius4),
        onClick = {
            touchFeedback.performMediumImpact()
            onPressed(video)
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AnyImage(
                modifier = Modifier.fillMaxSize(),
                image = video.getThumbnailUrl(),
            )
            Icon(
                modifier = Modifier
                    .size(Dimens.icon50)
                    .align(Alignment.Center),
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = Color.White,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.withA40()),
            )
        }
    }
}
