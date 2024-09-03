package feature.profile.presentation.profile

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.auth.domain.AppUser

@MviIntent
sealed class ProfileIntent {
    data object LogoutPressed : ProfileIntent()
}

@MviSideEffect
sealed class ProfileSideEffect {
    data object GoToLoginScreen : ProfileSideEffect()
}

@MviState
data class ProfileState(
    val appUser: AppUser? = null,
    val isLoading: Boolean = false,
)
