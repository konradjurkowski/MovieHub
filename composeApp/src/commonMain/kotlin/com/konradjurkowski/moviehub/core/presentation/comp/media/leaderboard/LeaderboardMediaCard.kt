package com.konradjurkowski.moviehub.core.presentation.comp.media.leaderboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.presentation.comp.image.AnyImage
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.presentation.theme.bronzeBorderColor
import com.konradjurkowski.moviehub.core.presentation.theme.goldBorderColor
import com.konradjurkowski.moviehub.core.presentation.theme.silverBorderColor
import com.konradjurkowski.moviehub.core.presentation.theme.withA10
import com.konradjurkowski.moviehub.core.presentation.theme.withA40
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.extensions.confirm

@Composable
fun LeaderboardMediaCard(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String?,
    position: Int,
    rating: Double,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val shape = RoundedCornerShape(Dimens.radius10)
    val borderColor = when (position) {
        1 -> goldBorderColor
        2 -> silverBorderColor
        3 -> bronzeBorderColor
        else -> MaterialTheme.colorScheme.onBackground.withA40()
    }
    val textColor = when (position) {
        1 -> goldBorderColor
        2 -> silverBorderColor
        3 -> bronzeBorderColor
        else -> MaterialTheme.colorScheme.onBackground
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.padding18)
                .clip(RoundedCornerShape(Dimens.radius16))
                .clickable {
                    hapticFeedback.confirm()
                    onClick()
                },
            color = MaterialTheme.colorScheme.onBackground.withA10(),
            shape = RoundedCornerShape(Dimens.radius16),
            border = BorderStroke(width = Dimens.border1, color = borderColor),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding10, vertical = Dimens.padding8),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnyImage(
                    modifier = Modifier
                        .size(65.dp)
                        .clip(shape),
                    image = imageUrl,
                )
                RegularSpacer()
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium,
                    )
                    SmallSpacer()
                    RatingStars(rating = rating)
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(36.dp)
                .clip(shape)
                .background(color = MaterialTheme.colorScheme.surface)
                .border(width = Dimens.border2, color = borderColor, shape = shape),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = position.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = textColor,
            )
        }
    }
}
