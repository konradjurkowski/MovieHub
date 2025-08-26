package com.konradjurkowski.moviehub.feature.auth.presentation.welcome

import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.LoginRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.RegisterRoute
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.WelcomeIntent.LoginPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.WelcomeIntent.RegisterPressed

class WelcomeViewModel(
    private val appNavigator: AppNavigator,
) : BaseViewModel<WelcomeIntent, WelcomeState, WelcomeEvent>(
    initialState = WelcomeState,
) {

    override fun processIntent(intent: WelcomeIntent) {
        when (intent) {
            LoginPressed -> appNavigator.push(LoginRoute)
            RegisterPressed -> appNavigator.push(RegisterRoute)
        }
    }
}
