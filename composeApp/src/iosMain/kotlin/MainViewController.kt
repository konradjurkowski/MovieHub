import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import core.tools.haptic.IOSHapticFeedback
import core.utils.LocalTouchFeedback

fun MainViewController() = ComposeUIViewController(
    configure = {
        onFocusBehavior = OnFocusBehavior.FocusableAboveKeyboard
    }
) {
    CompositionLocalProvider(
        LocalTouchFeedback provides IOSHapticFeedback(),
    ) {
        App()
    }
}
