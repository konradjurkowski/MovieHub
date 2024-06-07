package core.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.FirebaseTooManyRequestsException
import dev.gitlive.firebase.auth.FirebaseAuthException
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Modifier.clearFocus(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        focusManager.clearFocus()
    }
}

@Composable
fun TextUnit.toDp(): Dp {
    return with(LocalDensity.current) {
        this@toDp.toDp()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StringResource.toDisplay(): String {
    return stringResource(this)
}

fun getFailureMessage(error: Throwable): String {
    return when (error) {
        is FirebaseNetworkException -> {
            "Something went wrong, check your Internet connection and try again."
        }

        is FirebaseAuthException -> {
            "Please check your email and password and try again."
        }

        is FirebaseTooManyRequestsException -> {
            "Request failed due to too many attempts. Please try again later."
        }

        else -> {
            "Something went wrong, check your Internet connection and try again."
        }
    }
}
