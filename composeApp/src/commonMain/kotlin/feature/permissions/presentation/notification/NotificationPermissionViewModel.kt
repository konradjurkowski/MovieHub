package feature.permissions.presentation.notification

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.utils.requestPermission
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import feature.permissions.presentation.notification.NotificationPermissionIntent.AllowPressed
import feature.permissions.presentation.notification.NotificationPermissionIntent.DenyPressed
import feature.permissions.presentation.notification.NotificationPermissionSideEffect.GoToHomeScreen
import kotlinx.coroutines.launch

class NotificationPermissionViewModel(
    val permissionsController: PermissionsController,
) : BaseViewModel<NotificationPermissionIntent, NotificationPermissionSideEffect, NotificationPermissionState>() {

    override fun getDefaultState() = NotificationPermissionState

    override fun processIntent(intent: NotificationPermissionIntent) {
        when (intent) {
            AllowPressed -> onAllowPressed()
            DenyPressed -> sendSideEffect(GoToHomeScreen)
        }
    }

    private fun onAllowPressed() {
        screenModelScope.launch {
            permissionsController.requestPermission(Permission.REMOTE_NOTIFICATION)
            sendSideEffect(GoToHomeScreen)
        }
    }
}
