package feature.auth.presentation.splash

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import feature.auth.presentation.login.LoginScreenRoot
import feature.auth.presentation.splash.components.SplashScreen
import feature.auth.presentation.splash.SplashSideEffect.GoToHome
import feature.auth.presentation.splash.SplashSideEffect.GoToLogin
import feature.auth.presentation.splash.SplashSideEffect.GoToNotificationPermission
import feature.home.presentation.main.MainScreenRoot
import feature.permissions.presentation.notification.NotificationPermissionScreenRoot
import org.koin.core.parameter.parametersOf

class SplashScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val factory = rememberPermissionsControllerFactory()
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<SplashViewModel> { parametersOf(factory.createPermissionsController()) }

        BindEffect(viewModel.permissionsController)

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                GoToHome -> navigator.replace(MainScreenRoot())
                GoToLogin -> navigator.replace(LoginScreenRoot())
                GoToNotificationPermission -> navigator.replace(NotificationPermissionScreenRoot())
            }
        }

        SplashScreen()
    }
}
