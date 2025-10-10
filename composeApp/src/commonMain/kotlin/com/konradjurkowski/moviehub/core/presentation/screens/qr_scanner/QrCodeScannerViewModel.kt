package com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.events.EventBus
import com.konradjurkowski.moviehub.core.domain.model.QrCodeScanned
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.HideImagePicker
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.OnScanningFailure
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.OpenImagePicker
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.QrCodeChanged
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerEvent.ShowError
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.ToggleFlashlight
import kotlinx.coroutines.launch

class QrCodeScannerViewModel(
    private val navigator: AppNavigator,
    private val eventBus: EventBus,
) : BaseViewModel<QrCodeScannerIntent, QrCodeScannerState, QrCodeScannerEvent>(
    initialState = QrCodeScannerState(),
) {

    override fun processIntent(intent: QrCodeScannerIntent) {
        when (intent) {
            HideImagePicker -> updateState { copy(showImagePicker = false) }
            OpenImagePicker -> updateState { copy(showImagePicker = true) }
            ToggleFlashlight -> updateState { copy(flashlightOn = !flashlightOn) }
            is OnScanningFailure -> sendEvent(ShowError(intent.message))

            is QrCodeChanged -> {
                viewModelScope.launch {
                    eventBus.emit(QrCodeScanned(intent.qrCode))
                    navigator.back()
                }
            }
        }
    }
}
