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
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import feature.auth.presentation.register.RegisterSideEffect.GoToHome
import feature.auth.presentation.register.RegisterSideEffect.GoToNotificationPermission
import feature.auth.presentation.register.RegisterSideEffect.NavigateBack
import feature.auth.presentation.register.RegisterSideEffect.ShowError
import feature.auth.presentation.register.components.RegisterScreen
import feature.home.presentation.main.MainScreenRoot
import feature.permissions.presentation.notification.NotificationPermissionScreenRoot
import org.koin.core.parameter.parametersOf

class RegisterScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val factory = rememberPermissionsControllerFactory()
        val navigator = LocalNavigator.currentOrThrow
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<RegisterViewModel> { parametersOf(factory.createPermissionsController()) }
        val state by viewModel.viewState.collectAsState()

        BindEffect(viewModel.permissionsController)

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
