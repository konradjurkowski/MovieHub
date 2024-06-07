package feature.auth.presentation.login

import LocalSnackbarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.touchlab.kermit.Logger
import core.architecture.CollectSideEffects
import core.utils.getFailureMessage
import feature.auth.presentation.forgot_password.ForgotPasswordScreen
import feature.auth.presentation.login.components.LoginContent

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<LoginViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                LoginSideEffect.GoToForgotPassword -> navigator.push(ForgotPasswordScreen())
                LoginSideEffect.GoToHome -> Logger.i { "TAG: LOGIN TO HOME PAGE" }
                is LoginSideEffect.ShowError -> {
                    snackbarState.showError(getFailureMessage(effect.error))
                }
            }
        }

        LoginContent(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
