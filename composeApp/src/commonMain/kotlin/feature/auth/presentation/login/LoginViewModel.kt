package feature.auth.presentation.login

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.validator.FormValidator
import core.utils.Resource
import feature.auth.data.remote.AuthService
import kotlinx.coroutines.launch

class LoginViewModel(
    private val formValidator: FormValidator,
    private val authService: AuthService,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<LoginIntent, LoginSideEffect, LoginState>() {
    override fun getDefaultState(): LoginState = LoginState()

    override fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> updateViewState { copy(email = intent.email) }
            is LoginIntent.PasswordChanged -> updateViewState { copy(password = intent.password) }
            LoginIntent.TogglePasswordVisibility -> {
                updateViewState { copy(obscurePassword = !obscurePassword) }
            }
            LoginIntent.ForgotPasswordPressed -> sendSideEffect(LoginSideEffect.GoToForgotPassword)
            is LoginIntent.SignIn -> signIn(intent.email, intent.password)
            LoginIntent.CreateAccountPressed -> sendSideEffect(LoginSideEffect.GoToRegister)
        }
    }

    private fun signIn(email: String, password: String) {
        if (viewState.value.loginState.isLoading()) return

        val emailValidation = formValidator.validateEmail(email)
        val passwordValidation = formValidator.basicValidation(password)
        updateViewState {
            copy(
                emailValidation = emailValidation,
                passwordValidation = passwordValidation,
            )
        }

        if (!emailValidation.successful || !passwordValidation.successful) return

        updateViewState { copy(loginState = Resource.Loading) }
        screenModelScope.launch(dispatchersProvider.io) {
            val result = authService.signIn(email, password)

            when (result) {
                is Resource.Success -> sendSideEffect(LoginSideEffect.GoToHome)
                is Resource.Failure -> sendSideEffect(LoginSideEffect.ShowError(result.error))
                else -> {
                    // NO - OP
                }
            }

            updateViewState { copy(loginState = result) }
        }
    }
}
