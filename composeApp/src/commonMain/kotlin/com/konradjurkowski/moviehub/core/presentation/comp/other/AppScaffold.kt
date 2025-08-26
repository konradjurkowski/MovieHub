package com.konradjurkowski.moviehub.core.presentation.comp.other

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FabPosition.Companion.End
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.utils.clearFocus

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    keepBottomBarBelowKeyboard: Boolean = false,
    content: @Composable (PaddingValues) -> Unit,
) {
    if (!keepBottomBarBelowKeyboard) {
        Scaffold(
            modifier = Modifier
                .imePadding()
                .clearFocus(),
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            containerColor = containerColor,
            contentColor = contentColor,
            contentWindowInsets = contentWindowInsets,
            content = content,
        )
        return
    }

    val density = LocalDensity.current
    val imeInsets = WindowInsets.ime
    val (measuredBottomBar, bottomBarHeight) = rememberMeasuredContent(bottomBar)

    Scaffold(
        modifier = modifier.clearFocus(),
        topBar = topBar,
        bottomBar = measuredBottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
    ) { contentPadding ->
        val imeHeight = with(density) { imeInsets.getBottom(density).toDp() }
        val adjustedImePadding = if (imeHeight > 0.dp) maxOf(0.dp, imeHeight - bottomBarHeight) else 0.dp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = adjustedImePadding),
        ) { content(contentPadding) }
    }
}

@Composable
private fun rememberMeasuredContent(
    content: @Composable () -> Unit,
): Pair<@Composable () -> Unit, Dp> {
    val density = LocalDensity.current
    var height by remember { mutableStateOf(0.dp) }

    val measurableContent: @Composable () -> Unit = {
        Box(
            modifier = Modifier.onGloballyPositioned { coordinates ->
                height = with(density) { coordinates.size.height.toDp() }
            },
        ) { content() }
    }

    return measurableContent to height
}
