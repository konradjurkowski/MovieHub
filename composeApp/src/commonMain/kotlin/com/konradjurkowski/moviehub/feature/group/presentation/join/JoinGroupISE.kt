package com.konradjurkowski.moviehub.feature.group.presentation.join

import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.core.domain.model.ActionState

@MviIntent
sealed class JoinGroupIntent {
    data class InvitationCodeChanged(val code: String) : JoinGroupIntent()
    data class JoinGroupPressed(val code: String) : JoinGroupIntent()
    data object ScanQrCodePressed : JoinGroupIntent()
    data object CreateGroupPressed : JoinGroupIntent()
    data object OpenAppSettings: JoinGroupIntent()
    data object DismissPermissionDialog : JoinGroupIntent()
}

@MviEvent
data object JoinGroupEvent

@MviState
data class JoinGroupState(
    val invitationCode: String = "",
    val showPermissionDialog: Boolean = false,
    val joinState: ActionState = ActionState.Idle,
)
