package feature.auth.presentation.register

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.validator.FormValidator
import core.utils.Resource
import feature.auth.data.remote.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val formValidator: FormValidator,
    private val authService: AuthService
) : BaseViewModel<RegisterIntent, RegisterSideEffect, RegisterState>() {
    override fun getDefaultState(): RegisterState = RegisterState()

    override fun processIntent(intent: RegisterIntent) {
        when (intent) {
            RegisterIntent.BackPressed -> sendSideEffect(RegisterSideEffect.NavigateBack)
            is RegisterIntent.NameChanged -> updateViewState { copy(name = intent.name) }
            is RegisterIntent.EmailChanged -> updateViewState { copy(email = intent.email) }
            is RegisterIntent.PasswordChanged -> updateViewState { copy(password = intent.password) }
            is RegisterIntent.RepeatedPasswordChanged -> {
                updateViewState { copy(repeatedPassword = intent.repeatedPassword) }
            }
            RegisterIntent.TogglePasswordVisibility -> updateViewState { copy(obscurePassword = !obscurePassword) }
            RegisterIntent.ToggleRepeatedPasswordVisibility -> {
                updateViewState { copy(obscureRepeatedPassword = !obscureRepeatedPassword) }
            }
            is RegisterIntent.SignUp -> signUp(
                name = intent.name,
                email = intent.email,
                password = intent.password,
                repeatedPassword = intent.repeatedPassword,
            )
        }
    }

    private fun signUp(
        name: String,
        email: String,
        password: String,
        repeatedPassword: String,
    ) {
        if (viewState.value.registerState.isLoading()) return

        val nameValidation = formValidator.basicValidation(name)
        val emailValidation = formValidator.validateEmail(email)
        val passwordValidation = formValidator.validatePassword(password)
        val repeatedPasswordValidation =
            formValidator.validateRepeatedPassword(password, repeatedPassword)

        updateViewState {
            copy(
                nameValidation = nameValidation,
                emailValidation = emailValidation,
                passwordValidation = passwordValidation,
                repeatedPasswordValidation = repeatedPasswordValidation,
            )
        }

        if (!emailValidation.successful || !passwordValidation.successful ||
            !passwordValidation.successful || !repeatedPasswordValidation.successful) return

        updateViewState { copy(registerState = Resource.Loading) }
        screenModelScope.launch(Dispatchers.IO) {
            val result = authService.signUp(name, email, password)

            when (result) {
                is Resource.Success -> sendSideEffect(RegisterSideEffect.GoToHome)
                is Resource.Failure -> sendSideEffect(RegisterSideEffect.ShowError(result.error))
                else -> {
                    // NO - OP
                }
            }

            updateViewState { copy(registerState = result) }
        }
    }
}
