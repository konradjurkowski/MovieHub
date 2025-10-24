package com.konradjurkowski.moviehub.feature.auth.presentation.welcome.ise

import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviState

@MviIntent
sealed class WelcomeIntent {
    data object LoginPressed : WelcomeIntent()
    data object RegisterPressed : WelcomeIntent()
}

@MviEvent
data object WelcomeEvent

@MviState
data object WelcomeState
