package feature.permissions.presentation.notification

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState

@MviIntent
sealed class NotificationPermissionIntent {
    data object AllowPressed : NotificationPermissionIntent()
    data object DenyPressed : NotificationPermissionIntent()
}

@MviSideEffect
sealed class NotificationPermissionSideEffect {
    data object GoToHomeScreen : NotificationPermissionSideEffect()
}

@MviState
data object NotificationPermissionState
