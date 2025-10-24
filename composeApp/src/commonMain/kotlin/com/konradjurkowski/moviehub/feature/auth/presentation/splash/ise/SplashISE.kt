package com.konradjurkowski.moviehub.feature.auth.presentation.splash.ise

import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviState

@MviIntent
sealed class SplashIntent {
    data object TryAgainPressed : SplashIntent()
}

@MviEvent
data object SplashEvent

@MviState
sealed class SplashState {
    data object Loading : SplashState()
    data object Error : SplashState()
}
