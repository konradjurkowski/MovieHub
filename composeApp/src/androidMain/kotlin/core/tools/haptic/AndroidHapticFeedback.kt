package core.tools.haptic

import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

class AndroidHapticFeedback(
    private val hapticFeedback: HapticFeedback,
) : TouchFeedback {

    override fun performLightImpact() {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }

    override fun performMediumImpact() {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    override fun performHeavyImpact() {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }
}
