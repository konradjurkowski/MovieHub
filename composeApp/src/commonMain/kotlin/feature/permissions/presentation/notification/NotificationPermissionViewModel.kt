package feature.permissions.presentation.notification

import core.architecture.BaseViewModel
import feature.permissions.presentation.notification.NotificationPermissionIntent.AllowPressed
import feature.permissions.presentation.notification.NotificationPermissionIntent.DenyPressed
import feature.permissions.presentation.notification.NotificationPermissionSideEffect.CheckNotificationPermission
import feature.permissions.presentation.notification.NotificationPermissionSideEffect.GoToHomeScreen

class NotificationPermissionViewModel :
    BaseViewModel<NotificationPermissionIntent, NotificationPermissionSideEffect, NotificationPermissionState>() {

    override fun getDefaultState() = NotificationPermissionState

    override fun processIntent(intent: NotificationPermissionIntent) {
        when (intent) {
            AllowPressed -> sendSideEffect(CheckNotificationPermission)
            DenyPressed -> sendSideEffect(GoToHomeScreen)
        }
    }

    fun onPermissionResult() = sendSideEffect(GoToHomeScreen)
}
