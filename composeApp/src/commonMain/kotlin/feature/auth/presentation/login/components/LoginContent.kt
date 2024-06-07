package feature.auth.presentation.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import core.components.button.PrimaryButton
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.components.text.InteractiveText
import core.components.text_field.InputTextField
import core.components.text_field.InvalidFieldMessage
import core.components.text_field.TextFieldLabel
import core.components.top_bar.MainTopBar
import core.utils.Dimens
import core.utils.clearFocus
import core.utils.toDisplay
import feature.auth.presentation.login.LoginIntent
import feature.auth.presentation.login.LoginState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.email_address
import moviehub.composeapp.generated.resources.ic_visibility
import moviehub.composeapp.generated.resources.ic_visibility_off
import moviehub.composeapp.generated.resources.login_screen_forgot_password_label
import moviehub.composeapp.generated.resources.login_screen_login_label
import moviehub.composeapp.generated.resources.login_screen_title
import moviehub.composeapp.generated.resources.password
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginContent(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier.clearFocus(),
        topBar = {
            MainTopBar(
                title = stringResource(Res.string.login_screen_title),
                isLeadingVisible = false,
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(Dimens.regularPadding)
                .verticalScroll(rememberScrollState())
        ) {
            TextFieldLabel(text = stringResource(Res.string.email_address))
            SmallSpacer()
            InputTextField(
                value = state.email,
                onValueChanged = { onIntent(LoginIntent.EmailChanged(it)) },
                isError = state.loginState.isFailure() || !state.emailValidation.successful
            )
            InvalidFieldMessage(
                message = state.emailValidation.errorMessage?.toDisplay() ?: "",
                isInvalid = !state.emailValidation.successful
            )
            RegularSpacer()
            Row {
                TextFieldLabel(
                    modifier = Modifier.weight(1f),
                    text = stringResource(Res.string.password)
                )
                InteractiveText(text = stringResource(Res.string.login_screen_forgot_password_label)) {
                    onIntent(LoginIntent.ForgotPasswordPressed)
                }
            }
            SmallSpacer()
            InputTextField(
                value = state.password,
                onValueChanged = { onIntent(LoginIntent.PasswordChanged(it)) },
                obscure = state.obscurePassword,
                isError = state.loginState.isFailure() || !state.passwordValidation.successful,
                trailingIcon = {
                    IconButton(onClick = { onIntent(LoginIntent.TogglePasswordVisibility) }) {
                        Icon(
                            when (state.obscurePassword) {
                                true -> painterResource(Res.drawable.ic_visibility)
                                false -> painterResource(Res.drawable.ic_visibility_off)
                            },
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                            contentDescription = "visibility icon"
                        )
                    }
                }
            )
            InvalidFieldMessage(
                message = state.passwordValidation.errorMessage?.toDisplay() ?: "",
                isInvalid = !state.passwordValidation.successful
            )
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.login_screen_login_label),
                loading = state.loginState.isLoading(),
                onClick = {
                    focusManager.clearFocus()
                    onIntent(LoginIntent.SignIn(state.email, state.password))
                }
            )
        }
    }
}
