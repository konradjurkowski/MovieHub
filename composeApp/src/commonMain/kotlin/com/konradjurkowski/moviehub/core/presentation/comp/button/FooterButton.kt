package com.konradjurkowski.moviehub.core.presentation.comp.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.extensions.confirm

@Composable
fun FooterButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    visible: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight } ),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight } ),
    ) {
        Row(
            modifier = modifier
                .clickable(enabled = enabled) {
                    hapticFeedback.confirm()
                    onClick()
                }
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .navigationBarsPadding()
                .padding(Dimens.padding16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
                SmallSpacer()
            }
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}
