package core.components.media.video

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.text.SectionTitle
import core.model.media.Video
import core.utils.Dimens

@Composable
fun MediaVideoList(
    modifier: Modifier = Modifier,
    videoList: List<Video>,
    onPressed: (Video) -> Unit,
) {
    if (videoList.isEmpty()) return

    Column(modifier = modifier) {
        HorizontalDivider(modifier = Modifier.padding(vertical = Dimens.padding16))
        SectionTitle(
            modifier = Modifier.padding(horizontal = Dimens.padding16),
            title = "Trailers",
        )
        VideoHorizontalList(
            modifier = Modifier.padding(top = Dimens.padding8),
            videoList = videoList,
            onPressed = onPressed,
        )
    }
}
