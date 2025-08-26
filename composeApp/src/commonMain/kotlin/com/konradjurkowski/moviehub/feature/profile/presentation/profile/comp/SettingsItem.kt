package com.konradjurkowski.moviehub.feature.profile.presentation.profile.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.theme.withA30
import com.konradjurkowski.moviehub.core.utils.Dimens

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    Column(
        modifier = modifier.clickable {
            onClick()
            hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.padding16),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
            )
            RegularSpacer()
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
            )
            RegularSpacer()
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.border1)
                .background(MaterialTheme.colorScheme.onBackground.withA30()),
        )
    }
}
