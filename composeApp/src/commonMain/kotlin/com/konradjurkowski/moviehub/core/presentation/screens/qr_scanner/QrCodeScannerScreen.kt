package com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerEvent.ShowError
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.components.QrCodeScannerContent
import com.konradjurkowski.snackbarkmm.LocalSnackbarState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.qr_code_scanner_screen_failure_to_scan
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QrCodeScannerScreen() {
    val snackBarState = LocalSnackbarState.current

    val viewModel = koinViewModel<QrCodeScannerViewModel>()
    val state by viewModel.viewState.collectAsState()

    CollectEvents(viewModel.viewEvents) { event ->
        when (event) {
            is ShowError -> snackBarState
                .showError(Res.string.qr_code_scanner_screen_failure_to_scan)
        }
    }

    QrCodeScannerContent(state = state, onIntent = viewModel::sendIntent)
}
