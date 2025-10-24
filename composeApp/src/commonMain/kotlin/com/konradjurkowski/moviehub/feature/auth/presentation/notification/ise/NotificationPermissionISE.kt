package com.konradjurkowski.moviehub.feature.auth.presentation.notification.ise

import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviState

@MviIntent
sealed class NotificationPermissionIntent {
    data object AllowPressed : NotificationPermissionIntent()
    data object DenyPressed : NotificationPermissionIntent()
}

@MviEvent
data object NotificationPermissionEvent

@MviState
data object NotificationPermissionState
