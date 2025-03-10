package core.tools.haptic

import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle

class IOSHapticFeedback : TouchFeedback {

    override fun performLightImpact() {
        performImpact(UIImpactFeedbackStyle.UIImpactFeedbackStyleLight)
    }

    override fun performMediumImpact() {
        performImpact(UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium)
    }

    override fun performHeavyImpact() {
        performImpact(UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy)
    }

    private fun performImpact(style: UIImpactFeedbackStyle) {
        val feedbackGenerator = UIImpactFeedbackGenerator(style = style)
        feedbackGenerator.prepare()
        feedbackGenerator.impactOccurred()
    }
}
