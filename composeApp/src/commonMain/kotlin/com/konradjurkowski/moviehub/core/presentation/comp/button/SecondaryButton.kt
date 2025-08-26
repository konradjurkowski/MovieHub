package com.konradjurkowski.moviehub.core.presentation.comp.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.konradjurkowski.moviehub.core.utils.Dimens

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.onPrimary,
    foregroundColor: Color = MaterialTheme.colorScheme.primary,
    verticalPadding: Dp = Dimens.padding8,
    horizontalPadding: Dp = Dimens.padding16,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val focusManager = LocalFocusManager.current

    OutlinedButton(
        modifier = modifier.heightIn(Dimens.defaultButtonHeight),
        onClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
            focusManager.clearFocus()
            onClick()
        },
        contentPadding = PaddingValues(
            vertical = verticalPadding,
            horizontal = horizontalPadding,
        ),
        border = BorderStroke(width = Dimens.border2, color = foregroundColor),
        shape = RoundedCornerShape(Dimens.radius12),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor,
            contentColor = foregroundColor,
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}
