package feature.permissions.presentation.notification

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.rememberPermissionState
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import feature.home.presentation.main.MainScreenRoot
import feature.permissions.presentation.notification.NotificationPermissionSideEffect.GoToHomeScreen
import feature.permissions.presentation.notification.components.NotificationPermissionScreen

class NotificationPermissionScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<NotificationPermissionViewModel>()

        val notificationPermissionState = rememberPermissionState(
            permission = Permission.Notification,
            onPermissionResult = { viewModel.onPermissionResult() },
        )

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                GoToHomeScreen -> navigator.replace(MainScreenRoot())
                NotificationPermissionSideEffect.CheckNotificationPermission -> notificationPermissionState.launchPermissionRequest()
            }
        }

        NotificationPermissionScreen(onIntent = viewModel::sendIntent)
    }
}
