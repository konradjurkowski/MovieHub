package com.konradjurkowski.moviehub.feature.auth.presentation.register.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.konradjurkowski.moviehub.core.presentation.comp.button.PrimaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.other.AppScaffold
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InputTextField
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InvalidFieldMessage
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.PasswordTextField
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.TextFieldLabel
import com.konradjurkowski.moviehub.core.presentation.comp.top_bar.MainTopBar
import com.konradjurkowski.moviehub.core.presentation.theme.withA40
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.ConfirmPasswordChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.EmailChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.NameChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.PasswordChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.RegisterPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.ToggleConfirmPasswordVisibility
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.TogglePasswordVisibility
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.register_screen_email_address
import moviehub.composeapp.generated.resources.register_screen_name
import moviehub.composeapp.generated.resources.register_screen_password
import moviehub.composeapp.generated.resources.register_screen_password_requirements
import moviehub.composeapp.generated.resources.register_screen_repeat_password
import moviehub.composeapp.generated.resources.register_screen_sign_up
import moviehub.composeapp.generated.resources.register_screen_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterContent(
    state: RegisterState,
    onIntent: (RegisterIntent) -> Unit,
) {
    AppScaffold(
        topBar = {
            MainTopBar(title = stringResource(Res.string.register_screen_title))
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
            TextFieldLabel(text = stringResource(Res.string.register_screen_name))
            SmallSpacer()
            InputTextField(
                value = state.name,
                onValueChange = { onIntent(NameChanged(it)) },
                imeAction = ImeAction.Next,
                error = state.registerState.isFailure() || !state.nameValidation.successful,
            )
            InvalidFieldMessage(result = state.nameValidation)
            RegularSpacer()
            TextFieldLabel(text = stringResource(Res.string.register_screen_email_address))
            SmallSpacer()
            InputTextField(
                value = state.email,
                onValueChange = { onIntent(EmailChanged(it)) },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                error = state.registerState.isFailure() || !state.emailValidation.successful,
            )
            InvalidFieldMessage(result = state.emailValidation)
            RegularSpacer()
            TextFieldLabel(text = stringResource(Res.string.register_screen_password))
            SmallSpacer()
            PasswordTextField(
                value = state.password,
                onValueChange = { onIntent(PasswordChanged(it)) },
                obscure = state.obscurePassword,
                imeAction = ImeAction.Next,
                isError = state.registerState.isFailure() || !state.passwordValidation.successful,
                onSuffixIconClick = { onIntent(TogglePasswordVisibility) },
            )
            InvalidFieldMessage(result = state.passwordValidation)
            RegularSpacer()
            TextFieldLabel(text = stringResource(Res.string.register_screen_repeat_password))
            SmallSpacer()
            PasswordTextField(
                value = state.confirmPassword,
                onValueChange = { onIntent(ConfirmPasswordChanged(it)) },
                obscure = state.obscureConfirmPassword,
                isError = state.registerState.isFailure() || !state.confirmPasswordValidation.successful,
                onSuffixIconClick = { onIntent(ToggleConfirmPasswordVisibility) },
            )
            InvalidFieldMessage(result = state.confirmPasswordValidation)
            SmallSpacer()
            Text(
                text = stringResource(Res.string.register_screen_password_requirements),
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground.withA40(),
            )
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.register_screen_sign_up),
                loading = state.registerState.isLoading(),
                onClick = {
                    val intent = RegisterPressed(
                        name = state.name,
                        email = state.email,
                        password = state.password,
                        confirmPassword = state.confirmPassword
                    )
                    onIntent(intent)
                }
            )
            RegularSpacer()
        }
    }
}
