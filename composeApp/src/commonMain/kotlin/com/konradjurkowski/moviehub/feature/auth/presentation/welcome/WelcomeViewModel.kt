package com.konradjurkowski.moviehub.feature.auth.presentation.welcome

import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.LoginRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.RegisterRoute
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.ise.WelcomeEvent
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.ise.WelcomeIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.ise.WelcomeIntent.LoginPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.ise.WelcomeIntent.RegisterPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.ise.WelcomeState
import com.konradjurkowski.weatherapp.BuildKonfig

class WelcomeViewModel(
    private val navigator: AppNavigator,
) : BaseViewModel<WelcomeIntent, WelcomeState, WelcomeEvent>(
    initialState = WelcomeState(versionNumber = BuildKonfig.VERSION_NAME),
) {

    override fun processIntent(intent: WelcomeIntent) {
        when (intent) {
            LoginPressed -> navigator.push(LoginRoute)
            RegisterPressed -> navigator.push(RegisterRoute)
        }
    }
}
