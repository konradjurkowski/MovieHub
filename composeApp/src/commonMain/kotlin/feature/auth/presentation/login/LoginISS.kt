package feature.auth.presentation.login

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import core.tools.validator.ValidationResult
import core.utils.Resource
import dev.gitlive.firebase.auth.FirebaseUser
import org.jetbrains.compose.resources.ExperimentalResourceApi

@MviIntent
sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data object TogglePasswordVisibility : LoginIntent()
    data object ForgotPasswordPressed : LoginIntent()
    data class SignIn(val email: String, val password: String) : LoginIntent()
}

@MviSideEffect
sealed class LoginSideEffect {
    data object GoToHome : LoginSideEffect()
    data object GoToForgotPassword : LoginSideEffect()
    data class ShowError(val error: Throwable) : LoginSideEffect()
}

@OptIn(ExperimentalResourceApi::class)
@MviState
data class LoginState(
    val email: String = "",
    val emailValidation: ValidationResult = ValidationResult(successful = true),
    val password: String = "",
    val obscurePassword: Boolean = true,
    val passwordValidation: ValidationResult = ValidationResult(successful = true),
    val loginState: Resource<FirebaseUser?> = Resource.Idle,
)