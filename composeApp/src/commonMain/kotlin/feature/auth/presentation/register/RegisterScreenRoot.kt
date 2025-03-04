package feature.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.isGranted
import com.mohamedrejeb.calf.permissions.rememberPermissionState
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import core.utils.isNotificationPermissionRequired
import feature.auth.presentation.register.RegisterSideEffect.NavigateBack
import feature.auth.presentation.register.RegisterSideEffect.NavigateForward
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

        val notificationPermissionState = rememberPermissionState(Permission.Notification)

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                NavigateBack -> navigator.pop()
                is ShowError -> snackbarState.showError(getFailureMessage(effect.error))

                NavigateForward -> {
                    if (!isNotificationPermissionRequired() || notificationPermissionState.status.isGranted) {
                        navigator.replaceAll(MainScreenRoot())
                        return@CollectSideEffects
                    }

                    navigator.replaceAll(NotificationPermissionScreenRoot())
                }
            }
        }

        RegisterScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
