package feature.auth.presentation.register

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import core.tools.validator.ValidationResult
import core.utils.Resource
import dev.gitlive.firebase.auth.FirebaseUser
import org.jetbrains.compose.resources.ExperimentalResourceApi

@MviIntent
sealed class RegisterIntent {
    data object BackPressed : RegisterIntent()
    data class NameChanged(val name: String) : RegisterIntent()
    data class EmailChanged(val email: String) : RegisterIntent()
    data class PasswordChanged(val password: String) : RegisterIntent()
    data object TogglePasswordVisibility : RegisterIntent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegisterIntent()
    data object ToggleRepeatedPasswordVisibility : RegisterIntent()
    data class SignUp(val email: String, val password: String) : RegisterIntent()
}

@MviSideEffect
sealed class RegisterSideEffect {
    data object NavigateBack : RegisterSideEffect()
    data object GoToHome : RegisterSideEffect()
    data class ShowError(val error: Throwable) : RegisterSideEffect()
}

@OptIn(ExperimentalResourceApi::class)
@MviState
data class RegisterState(
    val name: String = "",
    val nameValidation: ValidationResult = ValidationResult(successful = true),
    val email: String = "",
    val emailValidation: ValidationResult = ValidationResult(successful = true),
    val password: String = "",
    val obscurePassword: Boolean = true,
    val passwordValidation: ValidationResult = ValidationResult(successful = true),
    val repeatedPassword: String = "",
    val obscureRepeatedPassword: Boolean = true,
    val repeatedPasswordValidation: ValidationResult = ValidationResult(successful = true),
    val registerState: Resource<FirebaseUser?> = Resource.Idle,
)
