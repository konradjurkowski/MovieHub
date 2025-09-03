package com.konradjurkowski.moviehub.core.presentation.comp.media.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.core.domain.model.media.Video
import com.konradjurkowski.moviehub.core.presentation.comp.media.video.VideoHorizontalList
import com.konradjurkowski.moviehub.core.presentation.comp.text.SectionTitle
import com.konradjurkowski.moviehub.core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.trailer_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun MediaVideoList(
    modifier: Modifier = Modifier,
    videoList: List<Video>,
    onVideoClick: (Video) -> Unit,
) {
    if (videoList.isEmpty()) return

    Column(modifier = modifier) {
        HorizontalDivider(modifier = Modifier.padding(vertical = Dimens.padding16))
        SectionTitle(
            modifier = Modifier.padding(horizontal = Dimens.padding16),
            title = stringResource(Res.string.trailer_label),
        )
        VideoHorizontalList(
            modifier = Modifier.padding(top = Dimens.padding8),
            videoList = videoList,
            onVideoClick = onVideoClick,
        )
    }
}
