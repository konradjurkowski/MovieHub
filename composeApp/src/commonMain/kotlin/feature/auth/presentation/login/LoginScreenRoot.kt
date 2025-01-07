package feature.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.isGranted
import com.mohamedrejeb.calf.permissions.rememberPermissionState
import core.architecture.CollectSideEffects
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.auth.presentation.forgot_password.ForgotPasswordScreenRoot
import feature.auth.presentation.login.LoginSideEffect.GoToForgotPassword
import feature.auth.presentation.login.LoginSideEffect.GoToHome
import feature.auth.presentation.login.LoginSideEffect.GoToNotificationPermission
import feature.auth.presentation.login.LoginSideEffect.GoToRegister
import feature.auth.presentation.login.LoginSideEffect.ShowError
import feature.auth.presentation.login.components.LoginScreen
import feature.auth.presentation.register.RegisterScreenRoot
import feature.home.presentation.main.MainScreenRoot
import feature.permissions.presentation.notification.NotificationPermissionScreenRoot

class LoginScreenRoot : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<LoginViewModel>()
        val state by viewModel.viewState.collectAsState()

        val notificationPermissionState = rememberPermissionState(Permission.Notification)

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                GoToForgotPassword -> navigator.push(ForgotPasswordScreenRoot())
                GoToHome -> navigator.replace(MainScreenRoot())
                GoToRegister -> navigator.push(RegisterScreenRoot())

                GoToNotificationPermission -> {
                    if (notificationPermissionState.status.isGranted) {
                        navigator.replace(MainScreenRoot())
                        return@CollectSideEffects
                    }

                    navigator.replace(NotificationPermissionScreenRoot())
                }

                is ShowError -> {
                    snackbarState.showError(getFailureMessage(effect.error))
                }
            }
        }

        LoginScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
