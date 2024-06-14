package core.components.top_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.utils.LocalTouchFeedback

@Composable
fun NavigateBackArrow() {
    val navigator = LocalNavigator.currentOrThrow
    val touchFeedback = LocalTouchFeedback.current
    val focusManager = LocalFocusManager.current
    IconButton(
        onClick = {
            touchFeedback.performLongPress()
            focusManager.clearFocus()
            navigator.pop()
        },
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back Arrow",
        )
    }
}
