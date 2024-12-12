package feature.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.CollectSideEffects
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.auth.presentation.forgot_password.ForgotPasswordScreenRoot
import feature.auth.presentation.login.components.LoginScreen
import feature.auth.presentation.register.RegisterScreenRoot
import feature.home.presentation.main.MainScreenRoot

class LoginScreenRoot : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<LoginViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                LoginSideEffect.GoToForgotPassword -> navigator.push(ForgotPasswordScreenRoot())
                LoginSideEffect.GoToHome -> navigator.replace(MainScreenRoot())
                is LoginSideEffect.ShowError -> {
                    snackbarState.showError(getFailureMessage(effect.error))
                }
                LoginSideEffect.GoToRegister -> navigator.push(RegisterScreenRoot())
            }
        }

        LoginScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
