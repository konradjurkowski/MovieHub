package core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

/** Getting screen size info for UI-related calculations */
data class ScreenSizeInfo(val height: Dp, val width: Dp)

@Composable
expect fun getScreenSizeInfo(): ScreenSizeInfo
