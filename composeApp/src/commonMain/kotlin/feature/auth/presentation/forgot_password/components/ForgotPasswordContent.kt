package feature.auth.presentation.forgot_password.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import core.components.button.PrimaryButton
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.components.text_field.InputTextField
import core.components.text_field.InvalidFieldMessage
import core.components.text_field.TextFieldLabel
import core.components.top_bar.MainTopBar
import core.utils.Dimens
import core.utils.clearFocus
import core.utils.toDisplay
import feature.auth.presentation.forgot_password.ForgotPasswordIntent
import feature.auth.presentation.forgot_password.ForgotPasswordState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.forgot_password
import moviehub.composeapp.generated.resources.reset_password
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ForgotPasswordContent(
    state: ForgotPasswordState,
    onIntent: (ForgotPasswordIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.clearFocus(),
        topBar = {
            MainTopBar(title = stringResource(Res.string.forgot_password))
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(Dimens.regularPadding)
                .verticalScroll(rememberScrollState())
        ) {
            TextFieldLabel(text = stringResource(Res.string.forgot_password))
            SmallSpacer()
            InputTextField(
                value = state.email,
                onValueChanged = { onIntent(ForgotPasswordIntent.EmailChanged(it)) },
                isError = state.resetState.isFailure() || !state.emailValidation.successful
            )
            InvalidFieldMessage(
                message = state.emailValidation.errorMessage?.toDisplay() ?: "",
                isInvalid = !state.emailValidation.successful
            )
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.reset_password),
                loading = state.resetState.isLoading(),
                onClick = {
                    focusManager.clearFocus()
                    onIntent(ForgotPasswordIntent.ResetPassword(state.email))
                }
            )
        }
    }
}
