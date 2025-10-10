package com.konradjurkowski.moviehub.core.utils.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.utils.exceptions.CustomException
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.snackbarkmm.SnackBarState
import com.preat.peekaboo.image.picker.ImagePickerLauncher
import com.preat.peekaboo.image.picker.ResizeOptions
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import dev.gitlive.firebase.FirebaseNetworkException
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.StringResource

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
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        focusManager.clearFocus()
    }
}

fun Modifier.drawTopBorder(
    width: Dp = 2.dp,
    color: Color,
): Modifier {
    return this.drawBehind {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = width.toPx(),
        )
    }
}

fun Modifier.paddingForIndex(
    index: Int,
    size: Int,
    padding: Dp = Dimens.padding16,
    spacer: Dp = Dimens.padding4
): Modifier {
    return this.padding(
        start = if (index == 0) padding else spacer,
        end = if (index == size - 1) padding else 0.dp,
    )
}

@Composable
fun rememberImagePicker(onResult: (ByteArray) -> Unit): ImagePickerLauncher {
    val scope = rememberCoroutineScope()
    return rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        resizeOptions = ResizeOptions(compressionQuality = 0.5),
        onResult = { result ->
            val byteArray = result.firstOrNull() ?: return@rememberImagePickerLauncher
            onResult(byteArray)
        },
    )
}

fun SnackBarState.showError(error: Throwable) {
    this.showError(getFailureMessage(error))
}

fun getFailureMessage(error: Throwable): StringResource {
    return when (error) {
        is FirebaseNetworkException -> Res.string.something_went_wrong
        is CustomException -> error.messageRes
        else -> Res.string.something_went_wrong
    }
}
