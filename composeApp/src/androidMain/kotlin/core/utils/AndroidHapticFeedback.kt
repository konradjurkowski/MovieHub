package core.utils

import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

class AndroidHapticFeedback(
    private val hapticFeedback: HapticFeedback,
) : TouchFeedback {
    override fun performShortPress() {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }

    override fun performLongPress() {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }
}
