package feature.auth.presentation.splash

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.isGranted
import com.mohamedrejeb.calf.permissions.rememberPermissionState
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.utils.isNotificationPermissionRequired
import feature.auth.presentation.login.LoginScreenRoot
import feature.auth.presentation.splash.components.SplashScreen
import feature.auth.presentation.splash.SplashSideEffect.GoToHome
import feature.auth.presentation.splash.SplashSideEffect.GoToLogin
import feature.home.presentation.main.MainScreenRoot
import feature.permissions.presentation.notification.NotificationPermissionScreenRoot

class SplashScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<SplashViewModel>()

        val notificationPermissionState = rememberPermissionState(Permission.Notification)

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                GoToLogin -> navigator.replace(LoginScreenRoot())

                GoToHome -> {
                    if (!isNotificationPermissionRequired() || notificationPermissionState.status.isGranted) {
                        navigator.replace(MainScreenRoot())
                        return@CollectSideEffects
                    }

                    navigator.replace(NotificationPermissionScreenRoot())
                }
            }
        }

        SplashScreen()
    }
}
