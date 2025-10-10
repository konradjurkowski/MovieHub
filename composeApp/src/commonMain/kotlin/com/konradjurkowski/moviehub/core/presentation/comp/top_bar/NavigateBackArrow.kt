package com.konradjurkowski.moviehub.core.presentation.comp.top_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import com.konradjurkowski.moviehub.core.utils.extensions.canPop
import com.konradjurkowski.moviehub.core.utils.helpers.LocalNavController

@Composable
fun NavigateBackArrow() {
    val naviController = LocalNavController.current
    val hapticFeedback = LocalHapticFeedback.current
    val focusManager = LocalFocusManager.current

    if (naviController.canPop()) {
        IconButton(
            onClick = {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
                focusManager.clearFocus()
                naviController.navigateUp()
            },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back Arrow",
            )
        }
    }
}
