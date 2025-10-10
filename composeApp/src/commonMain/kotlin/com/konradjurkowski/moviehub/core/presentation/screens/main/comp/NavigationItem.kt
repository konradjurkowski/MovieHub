package com.konradjurkowski.moviehub.core.presentation.screens.main.comp

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.konradjurkowski.moviehub.core.domain.model.navigation.NavigationTab
import org.jetbrains.compose.resources.painterResource

@Composable
fun RowScope.NavigationItem(
    modifier: Modifier = Modifier,
    tab: NavigationTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    NavigationBarItem(
        modifier = modifier,
        selected = selected,
        onClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
            onClick()
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = Color.Transparent,
        ),
        label = { Text(text = tab.options.title) },
        icon = {
            Icon(
                painter = painterResource(tab.iconFor(selected)),
                contentDescription = tab.options.title,
            )
        }
    )
}
