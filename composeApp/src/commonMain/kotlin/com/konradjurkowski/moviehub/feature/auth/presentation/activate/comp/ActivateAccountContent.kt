package com.konradjurkowski.moviehub.feature.auth.presentation.activate.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.konradjurkowski.moviehub.core.presentation.comp.button.PrimaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.dialog.PermissionDialog
import com.konradjurkowski.moviehub.core.presentation.comp.loading.LoadingOverlay
import com.konradjurkowski.moviehub.core.presentation.comp.other.AppScaffold
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InputTextField
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InvalidFieldMessage
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.TextFieldLabel
import com.konradjurkowski.moviehub.core.presentation.comp.top_bar.MainTopBar
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.extensions.plainClickable
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.ActivateAccountPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.ActivationCodeChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.DismissPermissionDialog
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.OpenAppSettings
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.ResendCodePressed
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.ScanQrCodePressed
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.activate_account_screen_activate_button
import moviehub.composeapp.generated.resources.activate_account_screen_activation_code
import moviehub.composeapp.generated.resources.activate_account_screen_description
import moviehub.composeapp.generated.resources.activate_account_screen_resend_action
import moviehub.composeapp.generated.resources.activate_account_screen_resend_prefix
import moviehub.composeapp.generated.resources.activate_account_screen_scan_code
import moviehub.composeapp.generated.resources.activate_account_screen_title
import moviehub.composeapp.generated.resources.camera_permission_settings_instructions
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActivateAccountContent(
    state: ActivateAccountState,
    onIntent: (ActivateAccountIntent) -> Unit,
) {
    AppScaffold(
        topBar = {
            MainTopBar(title = stringResource(Res.string.activate_account_screen_title))
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = Dimens.padding16),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "${stringResource(Res.string.activate_account_screen_resend_prefix)} ",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    modifier = Modifier.plainClickable { onIntent(ResendCodePressed) },
                    text = stringResource(Res.string.activate_account_screen_resend_action),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                )
            }
        },
        keepBottomBarBelowKeyboard = true,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.padding16)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = stringResource(Res.string.activate_account_screen_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
            )
            RegularSpacer()
            TextFieldLabel(text = stringResource(Res.string.activate_account_screen_activation_code))
            SmallSpacer()
            InputTextField(
                value = state.code,
                onValueChange = { onIntent(ActivationCodeChanged(it)) },
                keyboardType = KeyboardType.Number,
                error = !state.codeValidation.successful,
            )
            InvalidFieldMessage(result = state.codeValidation)
            RegularSpacer()
            Row(modifier = Modifier.plainClickable { onIntent(ScanQrCodePressed) }) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
                SmallSpacer()
                Text(
                    text = stringResource(Res.string.activate_account_screen_scan_code),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            }
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.activate_account_screen_activate_button),
                loading = state.activationState.isLoading(),
                onClick = { onIntent(ActivateAccountPressed(state.code)) },
            )
        }
    }

    PermissionDialog(
        visible = state.showPermissionDialog,
        message = stringResource(Res.string.camera_permission_settings_instructions),
        onDismiss = { onIntent(DismissPermissionDialog) },
        onGoToAppSettingsClick = { onIntent(OpenAppSettings) },
    )

    LoadingOverlay(loading = state.resendState.isLoading())
}
