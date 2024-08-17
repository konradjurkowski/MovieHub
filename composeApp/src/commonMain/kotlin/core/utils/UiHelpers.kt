package core.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.konradjurkowski.snackbarkmp.SnackBarState
import core.components.loading.LoaderState
import core.tools.datetime_formatter.DateTimeFormatter

val LocalSnackbarState =
    compositionLocalOf<SnackBarState> { error("No SnackbarState provided") }

val LocalLoaderState = compositionLocalOf<LoaderState> { error("No LoaderState provided") }

val LocalTouchFeedback = compositionLocalOf<TouchFeedback> { error("No TouchFeedback provided") }

val LocalDateTimeFormatter =
    staticCompositionLocalOf<DateTimeFormatter> { error("No LocalDateTimeFormatter provided") }
