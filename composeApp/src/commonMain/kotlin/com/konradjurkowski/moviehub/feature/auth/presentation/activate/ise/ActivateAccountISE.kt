package com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise

import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.validation.ValidationResult

@MviIntent
sealed class ActivateAccountIntent {
    data class ActivationCodeChanged(val code: String) : ActivateAccountIntent()
    data class ActivateAccountPressed(val code: String) : ActivateAccountIntent()
    data object DismissPermissionDialog : ActivateAccountIntent()
    data object OpenAppSettings : ActivateAccountIntent()
    data object ResendCodePressed : ActivateAccountIntent()
    data object ScanQrCodePressed : ActivateAccountIntent()
}

@MviEvent
sealed class ActivateAccountEvent {
    data class ShowError(val error: Throwable) : ActivateAccountEvent()
}

@MviState
data class ActivateAccountState(
    val code: String = "",
    val codeValidation: ValidationResult = ValidationResult(successful = true),
    val showPermissionDialog: Boolean = false,
    val resendState: ActionState = ActionState.Idle,
    val activationState: ActionState = ActionState.Idle,
)
