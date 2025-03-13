package feature.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import core.utils.safePush
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
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
import org.koin.core.parameter.parametersOf

class LoginScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val factory = rememberPermissionsControllerFactory()
        val navigator = LocalNavigator.currentOrThrow
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<LoginViewModel> { parametersOf(factory.createPermissionsController()) }
        val state by viewModel.viewState.collectAsState()

        BindEffect(viewModel.permissionsController)

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                GoToForgotPassword -> navigator.safePush(ForgotPasswordScreenRoot())
                GoToHome -> navigator.replace(MainScreenRoot())
                GoToNotificationPermission -> navigator.replace(NotificationPermissionScreenRoot())
                GoToRegister -> navigator.safePush(RegisterScreenRoot())
                is ShowError -> snackbarState.showError(getFailureMessage(effect.error))
            }
        }

        LoginScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
