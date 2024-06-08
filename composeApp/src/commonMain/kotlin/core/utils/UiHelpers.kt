package core.utils

import androidx.compose.runtime.compositionLocalOf
import com.konradjurkowski.snackbarkmp.SnackBarState

val LocalSnackbarState =
    compositionLocalOf<SnackBarState> { error("No SnackbarHostState provided") }

val LocalTouchFeedback = compositionLocalOf<TouchFeedback> { error("No TouchFeedback provided") }
