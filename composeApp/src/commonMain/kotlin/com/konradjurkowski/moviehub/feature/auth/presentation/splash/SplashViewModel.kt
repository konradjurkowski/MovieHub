package com.konradjurkowski.moviehub.feature.auth.presentation.splash

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.navigation.GlobalDestination.MainRoute
import com.konradjurkowski.moviehub.core.utils.tools.DispatchersProvider
import com.konradjurkowski.moviehub.feature.auth.domain.model.User
import com.konradjurkowski.moviehub.feature.auth.domain.model.isInAnyGroup
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.WelcomeRoute
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.SplashIntent.TryAgainPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.SplashState.Error
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.SplashState.Loading
import com.konradjurkowski.moviehub.feature.group.navigation.GroupDestination.JoinGroupRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.datetime.Clock

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val appNavigator: AppNavigator,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<SplashIntent, SplashState, SplashEvent>(
    initialState = Loading,
) {

    companion object {
        const val SPLASH_DELAY = 1500L
        const val TIMEOUT = 5000L
    }

    init {
        checkAuthorization()
    }

    override fun processIntent(intent: SplashIntent) {
        when (intent) {
            TryAgainPressed -> checkAuthorization()
        }
    }

    private fun checkAuthorization() {
        updateState { Loading }
        viewModelScope.launch(dispatchersProvider.io) {
            if (!authRepository.isUserLoggedIn()) {
                delay(SPLASH_DELAY)
                appNavigator.replaceAll(WelcomeRoute)
                return@launch
            }

            val user = fetchUserWithTimeout()
            when {
                user == null -> updateState { Error }
//                !user.isInAnyGroup() -> appNavigator.replaceAll(JoinGroupRoute)
                else -> appNavigator.replaceAll(MainRoute)
            }
        }
    }

    private suspend fun fetchUserWithTimeout(): User? {
        val startTime = Clock.System.now().toEpochMilliseconds()
        val user = withTimeoutOrNull(TIMEOUT) {
            authRepository.getUserDetails().getSuccess()
        }
        val elapsedTime = Clock.System.now().toEpochMilliseconds() - startTime
        val remainingDelay = (SPLASH_DELAY - elapsedTime).coerceAtLeast(0)
        delay(remainingDelay)
        return user
    }
}
