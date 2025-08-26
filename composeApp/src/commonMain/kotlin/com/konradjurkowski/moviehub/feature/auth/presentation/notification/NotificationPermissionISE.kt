package com.konradjurkowski.moviehub.feature.auth.presentation.notification

import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviState

@MviIntent
sealed class NotificationPermissionIntent {
    data object AllowPressed : NotificationPermissionIntent()
    data object DenyPressed : NotificationPermissionIntent()
}

@MviEvent
sealed class NotificationPermissionEvent {
    data object GoToHomeScreen : NotificationPermissionEvent()
}

@MviState
data object NotificationPermissionState
