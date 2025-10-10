package com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.outlined.FlashlightOff
import androidx.compose.material.icons.outlined.FlashlightOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.konradjurkowski.moviehub.core.presentation.comp.button.AnimatedIconButton
import com.konradjurkowski.moviehub.core.presentation.comp.top_bar.MainTopBar
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.HideImagePicker
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.OnScanningFailure
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.OpenImagePicker
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.ToggleFlashlight
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerIntent.QrCodeChanged
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerState
import com.konradjurkowski.moviehub.core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.qr_code_scanner_screen_description
import moviehub.composeapp.generated.resources.qr_code_scanner_screen_title
import org.jetbrains.compose.resources.stringResource
import qrscanner.CameraLens
import qrscanner.QrScanner

@Composable
fun QrCodeScannerContent(
    state: QrCodeScannerState,
    onIntent: (QrCodeScannerIntent) -> Unit,
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Scaffold(
        topBar = {
            MainTopBar(title = stringResource(Res.string.qr_code_scanner_screen_title))
        },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .fillMaxSize(),
        ) {
            QrScanner(
                modifier = Modifier.fillMaxSize(),
                flashlightOn = state.flashlightOn,
                cameraLens = CameraLens.Back,
                openImagePicker = state.showImagePicker,
                onCompletion = { result ->
                    if (result.isNotEmpty()) onIntent(QrCodeChanged(result))
                },
                imagePickerHandler = { onIntent(HideImagePicker) },
                onFailure = { onIntent(OnScanningFailure(it)) },
                customOverlay = {
                    drawScannerOverlay(overlayBorderColor = primaryColor)
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(Dimens.padding16)
                    .align(Alignment.BottomCenter),
                text = stringResource(Res.string.qr_code_scanner_screen_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            AnimatedIconButton(
                modifier = Modifier
                    .padding(Dimens.padding16)
                    .align(Alignment.TopStart),
                icon = Icons.Default.Upload,
                onClick = { onIntent(OpenImagePicker) },
            )
            AnimatedIconButton(
                modifier = Modifier
                    .padding(Dimens.padding16)
                    .align(Alignment.TopEnd),
                icon = if (state.flashlightOn) Icons.Outlined.FlashlightOn else Icons.Outlined.FlashlightOff,
                onClick = { onIntent(ToggleFlashlight) },
            )
        }
    }
}
