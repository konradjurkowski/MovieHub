package com.konradjurkowski.moviehub.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.utils.helpers.ScreenSizeInfo

@Composable
actual fun rememberScreenSize(): ScreenSizeInfo {
    val config = LocalConfiguration.current

    return remember(config) {
        ScreenSizeInfo(
            height = config.screenHeightDp.dp,
            width = config.screenWidthDp.dp
        )
    }
}
