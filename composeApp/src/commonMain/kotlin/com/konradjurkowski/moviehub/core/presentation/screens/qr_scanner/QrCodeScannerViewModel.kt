package com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.events.EventBus
import com.konradjurkowski.moviehub.core.domain.model.QrCodeScanned
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise.QrCodeScannerEvent
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise.QrCodeScannerIntent.HideImagePicker
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise.QrCodeScannerIntent.OnScanningFailure
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise.QrCodeScannerIntent.OpenImagePicker
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise.QrCodeScannerIntent.QrCodeChanged
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise.QrCodeScannerEvent.ShowError
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise.QrCodeScannerIntent
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise.QrCodeScannerIntent.ToggleFlashlight
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.ise.QrCodeScannerState
import kotlinx.coroutines.launch

class QrCodeScannerViewModel(
    private val navigator: AppNavigator,
    private val eventBus: EventBus,
) : BaseViewModel<QrCodeScannerIntent, QrCodeScannerState, QrCodeScannerEvent>(
    initialState = QrCodeScannerState(),
) {

    private var qrCodeHandled = false

    override fun processIntent(intent: QrCodeScannerIntent) {
        when (intent) {
            HideImagePicker -> updateState { copy(showImagePicker = false) }
            OpenImagePicker -> updateState { copy(showImagePicker = true) }
            ToggleFlashlight -> updateState { copy(flashlightOn = !flashlightOn) }
            is OnScanningFailure -> sendEvent(ShowError(intent.message))

            is QrCodeChanged -> {
                if (qrCodeHandled) return
                qrCodeHandled = true
                viewModelScope.launch {
                    eventBus.emit(QrCodeScanned(intent.qrCode))
                    navigator.back()
                }
            }
        }
    }
}
