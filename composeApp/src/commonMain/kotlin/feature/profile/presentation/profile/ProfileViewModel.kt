package feature.profile.presentation.profile

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import feature.auth.data.remote.AuthService
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authService: AuthService,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<ProfileIntent, ProfileSideEffect, ProfileState>() {

    init {
        initializeListeners()
        loadInitialData()
    }

    override fun getDefaultState() = ProfileState()

    override fun processIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.LogoutPressed -> logout()
            ProfileIntent.EditProfilePressed -> sendSideEffect(ProfileSideEffect.GoToProfileEditScreen)
        }
    }

    private fun logout() {
        screenModelScope.launch(dispatchersProvider.io) {
            authService.logout()
            sendSideEffect(ProfileSideEffect.GoToLoginScreen)
        }
    }

    private fun loadInitialData() {
        screenModelScope.launch(dispatchersProvider.io) {
            authService.getAppUser()
        }
    }

    private fun initializeListeners() {
        authService.appUser.onEach {
            updateViewState { copy(appUser = it) }
        }.launchIn(screenModelScope)
    }
}
