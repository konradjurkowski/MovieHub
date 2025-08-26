package com.konradjurkowski.moviehub.feature.profile.presentation.profile.ise

import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.feature.auth.domain.model.User

@MviIntent
sealed class ProfileIntent {
    data object EditProfilePressed : ProfileIntent()
    data object LogoutPressed : ProfileIntent()
}

@MviEvent
sealed class ProfileEvent

@MviState
data class ProfileState(val user: User? = null)
