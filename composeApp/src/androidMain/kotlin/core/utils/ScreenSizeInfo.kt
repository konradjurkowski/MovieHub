package core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
actual fun getScreenSizeInfo(): ScreenSizeInfo {
    val config = LocalConfiguration.current

    return remember(config) {
        ScreenSizeInfo(
            height = config.screenHeightDp.dp,
            width = config.screenWidthDp.dp
        )
    }
}
