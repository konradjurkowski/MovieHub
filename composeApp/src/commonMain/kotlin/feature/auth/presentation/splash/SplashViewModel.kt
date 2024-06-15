package feature.auth.presentation.splash

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import feature.auth.data.remote.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authService: AuthService
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
                sendSideEffect(SplashSideEffect.GoToLogin)
            } else {
                sendSideEffect(SplashSideEffect.GoToHome)
            }
        }
    }
}
