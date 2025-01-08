package feature.auth.presentation.login

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.validator.FormValidator
import core.utils.Constants
import core.utils.Platform
import core.utils.PlatformInfo
import core.utils.Resource
import feature.auth.data.remote.AuthService
import feature.auth.presentation.login.LoginIntent.EmailChanged
import feature.auth.presentation.login.LoginIntent.PasswordChanged
import feature.auth.presentation.login.LoginIntent.TogglePasswordVisibility
import feature.auth.presentation.login.LoginIntent.ForgotPasswordPressed
import feature.auth.presentation.login.LoginIntent.SignIn
import feature.auth.presentation.login.LoginIntent.CreateAccountPressed
import feature.auth.presentation.login.LoginSideEffect.GoToForgotPassword
import feature.auth.presentation.login.LoginSideEffect.GoToHome
import feature.auth.presentation.login.LoginSideEffect.GoToNotificationPermission
import feature.auth.presentation.login.LoginSideEffect.GoToRegister
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
                is Resource.Success -> checkIfPermissionGranted()
                is Resource.Failure -> sendSideEffect(ShowError(result.error))
                else -> {
                    // NO - OP
                }
            }

            updateViewState { copy(loginState = result) }
        }
    }

    private fun checkIfPermissionGranted() {
        val platform = PlatformInfo.platform
        val sdkInt = PlatformInfo.sdkInt
        val requiresPermissionCheck = platform == Platform.IOS ||
                (platform == Platform.IOS && sdkInt >= Constants.ANDROID_13_VERSION_CODE)

        if (requiresPermissionCheck) {
            sendSideEffect(GoToNotificationPermission)
            return
        }

        sendSideEffect(GoToHome)
    }
}
