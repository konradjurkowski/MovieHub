package feature.auth.presentation.login

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.ActionState
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import core.tools.validator.FormValidator
import feature.auth.data.remote.AuthService
import feature.auth.presentation.login.LoginIntent.EmailChanged
import feature.auth.presentation.login.LoginIntent.PasswordChanged
import feature.auth.presentation.login.LoginIntent.TogglePasswordVisibility
import feature.auth.presentation.login.LoginIntent.ForgotPasswordPressed
import feature.auth.presentation.login.LoginIntent.SignIn
import feature.auth.presentation.login.LoginIntent.CreateAccountPressed
import feature.auth.presentation.login.LoginSideEffect.GoToForgotPassword
import feature.auth.presentation.login.LoginSideEffect.GoToRegister
import feature.auth.presentation.login.LoginSideEffect.NavigateForward
import feature.auth.presentation.login.LoginSideEffect.ShowError
import kotlinx.coroutines.launch

class LoginViewModel(
    private val formValidator: FormValidator,
    private val authService: AuthService,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<LoginIntent, LoginSideEffect, LoginState>() {
    override fun getDefaultState(): LoginState = LoginState()

    override fun processIntent(intent: LoginIntent) {
        when (intent) {
            is EmailChanged -> updateViewState { copy(email = intent.email) }
            is PasswordChanged -> updateViewState { copy(password = intent.password) }
            TogglePasswordVisibility -> updateViewState { copy(obscurePassword = !obscurePassword) }
            ForgotPasswordPressed -> sendSideEffect(GoToForgotPassword)
            is SignIn -> signIn(intent.email, intent.password)
            CreateAccountPressed -> sendSideEffect(GoToRegister)
        }
    }

    private fun signIn(email: String, password: String) {
        if (viewState.value.loginState.isLoading()) return

        val emailValidation = formValidator.validateEmail(email)
        val passwordValidation = formValidator.basicValidation(password)
        updateViewState {
            copy(emailValidation = emailValidation, passwordValidation = passwordValidation)
        }

        if (!emailValidation.successful || !passwordValidation.successful) return

        updateViewState { copy(loginState = ActionState.Loading) }
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = authService.signIn(email, password)) {
                is Response.Success -> {
                    updateViewState { copy(loginState = ActionState.Success) }
                    sendSideEffect(NavigateForward)
                }

                is Response.Failure -> {
                    updateViewState { copy(loginState = ActionState.Failure(result.error)) }
                    sendSideEffect(ShowError(result.error))
                }
            }
        }
    }
}
