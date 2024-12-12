package feature.auth.presentation.forgot_password

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import core.tools.validator.ValidationResult
import core.utils.Resource

@MviIntent
sealed class ForgotPasswordIntent {
    data object BackPressed : ForgotPasswordIntent()
    data class EmailChanged(val email: String) : ForgotPasswordIntent()
    data class ResetPassword(val email: String) : ForgotPasswordIntent()
}

@MviSideEffect
sealed class ForgotPasswordSideEffect {
    data object NavigateBack : ForgotPasswordSideEffect()
    data class ShowError(val error: Throwable) : ForgotPasswordSideEffect()
    data object GoToLogin : ForgotPasswordSideEffect()
}

@MviState
data class ForgotPasswordState(
    val email: String = "",
    val emailValidation: ValidationResult = ValidationResult(successful = true),
    val resetState: Resource<Unit> = Resource.Idle,
)
