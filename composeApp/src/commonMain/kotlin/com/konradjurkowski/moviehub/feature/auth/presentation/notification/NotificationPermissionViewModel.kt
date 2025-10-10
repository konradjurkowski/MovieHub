package com.konradjurkowski.moviehub.feature.auth.presentation.notification

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.navigation.CoreDestination.MainRoute
import com.konradjurkowski.moviehub.core.utils.helpers.requestNotificationPermission
import com.konradjurkowski.moviehub.feature.auth.presentation.notification.NotificationPermissionIntent.AllowPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.notification.NotificationPermissionIntent.DenyPressed
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class NotificationPermissionViewModel(
    val permissionsController: PermissionsController,
    private val navigator: AppNavigator,
) : BaseViewModel<NotificationPermissionIntent, NotificationPermissionState, NotificationPermissionEvent>(
    initialState = NotificationPermissionState,
) {

    override fun processIntent(intent: NotificationPermissionIntent) {
        when (intent) {
            AllowPressed -> onAllowPressed()
            DenyPressed -> navigator.replaceAll(MainRoute)
        }
    }

    private fun onAllowPressed() {
        viewModelScope.launch {
            permissionsController.requestNotificationPermission()
            navigator.replaceAll(MainRoute)
        }
    }
}
