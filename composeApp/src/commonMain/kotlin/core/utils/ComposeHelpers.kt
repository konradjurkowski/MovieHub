package core.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalFocusManager
import androidx.paging.LoadState
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.email_already_exists
import moviehub.composeapp.generated.resources.invalid_credentials
import moviehub.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        enabled = enabled,
        indication = null,
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

fun LoadState.isLoading() = this is LoadState.Loading
fun LoadState.isError() = this is LoadState.Error

@Composable
fun StringResource?.toDisplay(): String {
    return this?.let { stringResource(it) } ?: ""
}

fun getFailureMessage(error: Throwable): StringResource {
    return when (error) {
        is FirebaseAuthInvalidCredentialsException -> Res.string.invalid_credentials
        is FirebaseAuthUserCollisionException -> Res.string.email_already_exists
        is FirebaseNetworkException -> Res.string.something_went_wrong
        is CustomException -> error.messageRes
        else -> Res.string.something_went_wrong
    }
}
