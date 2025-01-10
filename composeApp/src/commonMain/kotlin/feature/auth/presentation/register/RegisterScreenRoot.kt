package feature.auth.presentation.register

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
import feature.auth.presentation.register.RegisterSideEffect.GoToHome
import feature.auth.presentation.register.RegisterSideEffect.GoToNotificationPermission
import feature.auth.presentation.register.RegisterSideEffect.NavigateBack
import feature.auth.presentation.register.RegisterSideEffect.ShowError
import feature.auth.presentation.register.components.RegisterScreen
import feature.home.presentation.main.MainScreenRoot
import feature.permissions.presentation.notification.NotificationPermissionScreenRoot

class RegisterScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<RegisterViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                GoToHome -> navigator.replaceAll(MainScreenRoot())
                GoToNotificationPermission -> navigator.replaceAll(NotificationPermissionScreenRoot())
                NavigateBack -> navigator.pop()
                is ShowError -> snackbarState.showError(getFailureMessage(effect.error))
            }
        }

        RegisterScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
