package feature.auth.presentation.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import feature.auth.presentation.register.RegisterIntent
import feature.auth.presentation.register.RegisterState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.email_address
import moviehub.composeapp.generated.resources.ic_visibility
import moviehub.composeapp.generated.resources.ic_visibility_off
import moviehub.composeapp.generated.resources.name
import moviehub.composeapp.generated.resources.password
import moviehub.composeapp.generated.resources.register_screen_password_requirements
import moviehub.composeapp.generated.resources.register_screen_register_label
import moviehub.composeapp.generated.resources.register_screen_title
import moviehub.composeapp.generated.resources.repeat_password
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
    state: RegisterState,
    onIntent: (RegisterIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .imePadding()
            .clearFocus(),
        topBar = {
            MainTopBar(title = stringResource(Res.string.register_screen_title))
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(Dimens.padding16),
        ) {
            TextFieldLabel(text = stringResource(Res.string.name))
            SmallSpacer()
            InputTextField(
                value = state.name,
                onValueChange = { onIntent(RegisterIntent.NameChanged(it)) },
                imeAction = ImeAction.Next,
                isError = state.registerState.isFailure() || !state.nameValidation.successful
            )
            InvalidFieldMessage(
                message = state.nameValidation.errorMessage.toDisplay(),
                isInvalid = !state.nameValidation.successful,
            )
            RegularSpacer()
            TextFieldLabel(text = stringResource(Res.string.email_address))
            SmallSpacer()
            InputTextField(
                value = state.email,
                onValueChange = { onIntent(RegisterIntent.EmailChanged(it)) },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                isError = state.registerState.isFailure() || !state.emailValidation.successful
            )
            InvalidFieldMessage(
                message = state.emailValidation.errorMessage.toDisplay(),
                isInvalid = !state.emailValidation.successful,
            )
            RegularSpacer()
            TextFieldLabel(text = stringResource(Res.string.password))
            SmallSpacer()
            InputTextField(
                value = state.password,
                onValueChange = { onIntent(RegisterIntent.PasswordChanged(it)) },
                obscure = state.obscurePassword,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                isError = state.registerState.isFailure() || !state.passwordValidation.successful,
                trailingIcon = {
                    IconButton(onClick = { onIntent(RegisterIntent.TogglePasswordVisibility) }) {
                        Icon(
                            when (state.obscurePassword) {
                                true -> painterResource(Res.drawable.ic_visibility)
                                false -> painterResource(Res.drawable.ic_visibility_off)
                            },
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                            contentDescription = "visibility icon",
                        )
                    }
                }
            )
            InvalidFieldMessage(
                message = state.passwordValidation.errorMessage.toDisplay(),
                isInvalid = !state.passwordValidation.successful,
            )
            RegularSpacer()
            TextFieldLabel(text = stringResource(Res.string.repeat_password))
            SmallSpacer()
            InputTextField(
                value = state.repeatedPassword,
                onValueChange = { onIntent(RegisterIntent.RepeatedPasswordChanged(it)) },
                obscure = state.obscureRepeatedPassword,
                keyboardType = KeyboardType.Password,
                isError = state.registerState.isFailure() || !state.repeatedPasswordValidation.successful,
                trailingIcon = {
                    IconButton(onClick = { onIntent(RegisterIntent.ToggleRepeatedPasswordVisibility) }) {
                        Icon(
                            when (state.obscureRepeatedPassword) {
                                true -> painterResource(Res.drawable.ic_visibility)
                                false -> painterResource(Res.drawable.ic_visibility_off)
                            },
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                            contentDescription = "visibility icon",
                        )
                    }
                }
            )
            InvalidFieldMessage(
                message = state.repeatedPasswordValidation.errorMessage.toDisplay(),
                isInvalid = !state.repeatedPasswordValidation.successful,
            )
            SmallSpacer()
            Text(
                text = stringResource(Res.string.register_screen_password_requirements),
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            )
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.register_screen_register_label),
                loading = state.registerState.isLoading(),
                onClick = {
                    focusManager.clearFocus()
                    onIntent(RegisterIntent
                        .SignUp(state.name, state.email, state.password, state.repeatedPassword))
                }
            )
        }
    }
}
