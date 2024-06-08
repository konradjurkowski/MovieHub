package core.utils

import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle

class IOSTouchFeedback : TouchFeedback {
    override fun performShortPress() {
        performImpact(UIImpactFeedbackStyle.UIImpactFeedbackStyleLight)
    }

    override fun performLongPress() {
        performImpact(UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium)
    }

    private fun performImpact(style: UIImpactFeedbackStyle) {
        val feedbackGenerator = UIImpactFeedbackGenerator(style = style)
        feedbackGenerator.prepare()
        feedbackGenerator.impactOccurred()
    }
}
