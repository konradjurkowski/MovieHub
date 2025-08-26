package com.konradjurkowski.moviehub.feature.auth.presentation.register

import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.ValidationResult

@MviIntent
sealed class RegisterIntent {
    data class NameChanged(val name: String) : RegisterIntent()
    data class EmailChanged(val email: String) : RegisterIntent()
    data class PasswordChanged(val password: String) : RegisterIntent()
    data object TogglePasswordVisibility : RegisterIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterIntent()
    data object ToggleConfirmPasswordVisibility : RegisterIntent()
    data class RegisterPressed(
        val name: String,
        val email: String,
        val password: String,
        val confirmPassword: String,
    ) : RegisterIntent()
}

@MviEvent
sealed class RegisterEvent {
    data class ShowError(val error: Throwable) : RegisterEvent()
}

@MviState
data class RegisterState(
    val name: String = "",
    val nameValidation: ValidationResult = ValidationResult(successful = true),
    val email: String = "",
    val emailValidation: ValidationResult = ValidationResult(successful = true),
    val password: String = "",
    val obscurePassword: Boolean = true,
    val passwordValidation: ValidationResult = ValidationResult(successful = true),
    val confirmPassword: String = "",
    val obscureConfirmPassword: Boolean = true,
    val confirmPasswordValidation: ValidationResult = ValidationResult(successful = true),
    val registerState: ActionState = ActionState.Idle,
)
