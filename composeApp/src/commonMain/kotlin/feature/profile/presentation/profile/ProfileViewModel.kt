package feature.profile.presentation.profile

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import feature.auth.data.remote.AuthService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authService: AuthService,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<ProfileIntent, ProfileSideEffect, ProfileState>() {

    init {
        screenModelScope.launch(dispatchersProvider.io) {
            authService.getAppUser()

            authService.appUser.collectLatest {
                updateViewState { copy(appUser = it) }
            }
        }
    }

    override fun getDefaultState() = ProfileState()

    override fun processIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.LogoutPressed -> logout()
        }
    }

    private fun logout() {
        screenModelScope.launch(dispatchersProvider.io) {
            authService.logout()
            sendSideEffect(ProfileSideEffect.GoToLoginScreen)
        }
    }
}
