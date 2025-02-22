import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import core.tools.haptic.IOSHapticFeedback
import core.utils.LocalTouchFeedback
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController(
    configure = {
        onFocusBehavior = OnFocusBehavior.FocusableAboveKeyboard
    }
) {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark

    CompositionLocalProvider(
        LocalTouchFeedback provides IOSHapticFeedback(),
    ) {
        App(isDarkTheme = isDarkTheme)
    }
}
