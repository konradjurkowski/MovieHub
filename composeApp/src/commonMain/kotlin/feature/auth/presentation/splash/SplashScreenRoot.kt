package feature.auth.presentation.splash

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.CollectSideEffects
import feature.auth.presentation.login.LoginScreenRoot
import feature.auth.presentation.splash.components.SplashScreen
import feature.home.presentation.main.MainScreenRoot

class SplashScreenRoot : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<SplashViewModel>()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                SplashSideEffect.GoToHome -> navigator.replace(MainScreenRoot())
                SplashSideEffect.GoToLogin -> navigator.replace(LoginScreenRoot())
            }
        }

        SplashScreen()
    }
}
