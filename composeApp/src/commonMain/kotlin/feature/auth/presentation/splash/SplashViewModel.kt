package feature.auth.presentation.splash

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.utils.isNotificationPermissionRequired
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import feature.auth.data.remote.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import feature.auth.presentation.splash.SplashSideEffect.GoToHome
import feature.auth.presentation.splash.SplashSideEffect.GoToLogin
import feature.auth.presentation.splash.SplashSideEffect.GoToNotificationPermission

class SplashViewModel(
    private val authService: AuthService,
    val permissionsController: PermissionsController,
) : BaseViewModel<SplashIntent, SplashSideEffect, SplashState>() {

    init {
        checkAuthorization()
    }

    override fun getDefaultState() = SplashState

    override fun processIntent(intent: SplashIntent) {
        // NO - OP
    }

    private fun checkAuthorization() {
        screenModelScope.launch {
            delay(1500)

            if (authService.currentUser == null) {
                sendSideEffect(GoToLogin)
                return@launch
            }

            val isNotificationPermissionGranted = permissionsController.isPermissionGranted(Permission.REMOTE_NOTIFICATION)
            if (!isNotificationPermissionRequired() || isNotificationPermissionGranted) {
                sendSideEffect(GoToHome)
                return@launch
            }

            sendSideEffect(GoToNotificationPermission)
        }
    }
}
