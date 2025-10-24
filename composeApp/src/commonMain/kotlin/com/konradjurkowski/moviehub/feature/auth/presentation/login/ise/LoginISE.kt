package com.konradjurkowski.moviehub.feature.auth.presentation.login.ise

import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.validation.ValidationResult

@MviIntent
sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data object TogglePasswordVisibility : LoginIntent()
    data object ForgotPasswordPressed : LoginIntent()
    data class LoginPressed(val email: String, val password: String) : LoginIntent()
}

@MviEvent
sealed class LoginEvent {
    data class ShowError(val error: Throwable) : LoginEvent()
}

@MviState
data class LoginState(
    val email: String = "",
    val emailValidation: ValidationResult = ValidationResult(successful = true),
    val password: String = "",
    val obscurePassword: Boolean = true,
    val passwordValidation: ValidationResult = ValidationResult(successful = true),
    val loginState: ActionState = ActionState.Idle,
)
