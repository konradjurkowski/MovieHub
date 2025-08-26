package com.konradjurkowski.moviehub.core.presentation.comp.image

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.user_placeholder

@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    image: Any?,
    actionIcon: ImageVector = Icons.Default.Add,
    onActionClick: (() -> Unit)? = null,
) {
    val hapticFeedback = LocalHapticFeedback.current

    Box(modifier = modifier) {
        Card(
            modifier = modifier.size(Dimens.largeUserAvatar),
            shape = CircleShape,
            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
            elevation = CardDefaults
                .cardElevation(defaultElevation = Dimens.defaultElevation),
        ) {
            AnyImage(
                modifier = Modifier.fillMaxSize(),
                image = image,
                placeholderRes = Res.drawable.user_placeholder,
            )
        }
        onActionClick?.let {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(Dimens.icon32)
                    .offset(x = Dimens.padding8, y = -Dimens.padding8),
                color = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
                        onActionClick.invoke()
                    },
                ) {
                    Icon(
                        imageVector = actionIcon,
                        tint = MaterialTheme.colorScheme.background,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
