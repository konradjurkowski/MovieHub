package com.konradjurkowski.moviehub.feature.auth.presentation.login.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.konradjurkowski.moviehub.core.presentation.comp.button.PrimaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.other.AppScaffold
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.text.InteractiveText
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InputTextField
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InvalidFieldMessage
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.PasswordTextField
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.TextFieldLabel
import com.konradjurkowski.moviehub.core.presentation.comp.top_bar.MainTopBar
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.EmailChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.ForgotPasswordPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.LoginPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.PasswordChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.TogglePasswordVisibility
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.login_screen_email_address
import moviehub.composeapp.generated.resources.login_screen_forgot_password
import moviehub.composeapp.generated.resources.login_screen_password
import moviehub.composeapp.generated.resources.login_screen_sign_in
import moviehub.composeapp.generated.resources.login_screen_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginContent(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
) {
    AppScaffold(
        topBar = {
            MainTopBar(title = stringResource(Res.string.login_screen_title))
        },
        keepBottomBarBelowKeyboard = true,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = Dimens.padding16)
                .verticalScroll(rememberScrollState()),
        ) {
            TextFieldLabel(text = stringResource(Res.string.login_screen_email_address))
            SmallSpacer()
            InputTextField(
                value = state.email,
                onValueChange = { onIntent(EmailChanged(it)) },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                error = state.loginState.isFailure() || !state.emailValidation.successful,
            )
            InvalidFieldMessage(result = state.emailValidation)
            RegularSpacer()
            Row {
                TextFieldLabel(
                    modifier = Modifier.weight(1f),
                    text = stringResource(Res.string.login_screen_password),
                )
                InteractiveText(text = stringResource(Res.string.login_screen_forgot_password)) {
                    onIntent(ForgotPasswordPressed)
                }
            }
            SmallSpacer()
            PasswordTextField(
                value = state.password,
                onValueChange = { onIntent(PasswordChanged(it)) },
                obscure = state.obscurePassword,
                keyboardType = KeyboardType.Password,
                isError = state.loginState.isFailure() || !state.passwordValidation.successful,
                onSuffixIconClick = { onIntent(TogglePasswordVisibility) },
            )
            InvalidFieldMessage(result = state.passwordValidation)
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.login_screen_sign_in),
                loading = state.loginState.isLoading(),
                onClick = { onIntent(LoginPressed(state.email, state.password)) },
            )
            RegularSpacer()
        }
    }
}
