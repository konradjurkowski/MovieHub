package com.konradjurkowski.moviehub.core.presentation.comp.media.video

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.domain.model.media.Video
import com.konradjurkowski.moviehub.core.utils.extensions.paddingForIndex

@Composable
fun VideoHorizontalList(
    modifier: Modifier = Modifier,
    videoList: List<Video>,
    onVideoClick: (Video) -> Unit,
) {
    LazyRow(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth(),
    ) {
        itemsIndexed(videoList) { index, video ->
            VideoCard(
                modifier = Modifier.paddingForIndex(index = index, size = videoList.size),
                video = video,
                onClick = onVideoClick,
            )
        }
    }
}
