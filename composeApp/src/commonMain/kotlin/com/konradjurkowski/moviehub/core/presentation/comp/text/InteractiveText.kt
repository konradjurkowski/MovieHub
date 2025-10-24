package com.konradjurkowski.moviehub.core.presentation.comp.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.konradjurkowski.moviehub.core.utils.extensions.longPress
import com.konradjurkowski.moviehub.core.utils.extensions.noRippleClickable

@Composable
fun InteractiveText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
    text: String,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    Text(
        modifier = modifier.noRippleClickable {
            hapticFeedback.longPress()
            onClick()
        },
        text = text,
        style = textStyle,
        color = MaterialTheme.colorScheme.primary,
        textAlign = textAlign,
    )
}
