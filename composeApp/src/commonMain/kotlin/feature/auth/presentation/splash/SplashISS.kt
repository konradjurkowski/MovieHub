package feature.auth.presentation.splash

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState

@MviIntent
object SplashIntent

@MviSideEffect
sealed class SplashSideEffect {
    data object GoToHome : SplashSideEffect()
    data object GoToLogin : SplashSideEffect()
}

@MviState
object SplashState
