package com.konradjurkowski.moviehub.feature.profile.presentation.profile

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.utils.tools.DispatchersProvider
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.WelcomeRoute
import com.konradjurkowski.moviehub.feature.profile.presentation.profile.ise.ProfileEvent
import com.konradjurkowski.moviehub.feature.profile.presentation.profile.ise.ProfileIntent
import com.konradjurkowski.moviehub.feature.profile.presentation.profile.ise.ProfileIntent.EditProfilePressed
import com.konradjurkowski.moviehub.feature.profile.presentation.profile.ise.ProfileIntent.LogoutPressed
import com.konradjurkowski.moviehub.feature.profile.presentation.profile.ise.ProfileState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val navigator: AppNavigator,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<ProfileIntent, ProfileState, ProfileEvent>(
    initialState = ProfileState(),
) {

    init {
        loadInitialData()
        initializeListeners()
    }

    override fun processIntent(intent: ProfileIntent) {
        when (intent) {
            EditProfilePressed -> {}
            LogoutPressed -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch(dispatchersProvider.io) {
            authRepository.logout()
            authRepository.clearUserSession()
            navigator.replaceAll(WelcomeRoute)
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch(dispatchersProvider.io) {
            authRepository.getUserDetails()
        }
    }

    private fun initializeListeners() {
        viewModelScope.launch {
            authRepository.userFlow.collectLatest { user ->
                updateState { copy(user = user) }
            }
        }
    }
}
