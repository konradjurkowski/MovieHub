package com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise

import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviState

@MviIntent
sealed class QrCodeScannerIntent {
    data object ToggleFlashlight : QrCodeScannerIntent()
    data object OpenImagePicker : QrCodeScannerIntent()
    data object HideImagePicker : QrCodeScannerIntent()
    data class QrCodeChanged(val qrCode: String) : QrCodeScannerIntent()
    data class OnScanningFailure(val message: String) : QrCodeScannerIntent()
}

@MviEvent
sealed class QrCodeScannerEvent {
    data class ShowError(val message: String) : QrCodeScannerEvent()
}

@MviState
data class QrCodeScannerState(
    val flashlightOn: Boolean = false,
    val showImagePicker: Boolean = false,
)
