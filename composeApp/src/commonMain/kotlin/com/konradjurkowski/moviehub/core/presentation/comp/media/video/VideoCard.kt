package com.konradjurkowski.moviehub.core.presentation.comp.media.video

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
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.domain.model.media.Video
import com.konradjurkowski.moviehub.core.domain.model.media.thumbnailUrl
import com.konradjurkowski.moviehub.core.presentation.comp.image.AnyImage
import com.konradjurkowski.moviehub.core.presentation.theme.withA40
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.extensions.click

@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    video: Video,
    onClick: (Video) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    Card(
        modifier = modifier
            .height(150.dp)
            .aspectRatio(16/9f),
        shape = RoundedCornerShape(Dimens.radius4),
        onClick = {
            hapticFeedback.click()
            onClick(video)
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AnyImage(
                modifier = Modifier.fillMaxSize(),
                image = video.thumbnailUrl,
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
