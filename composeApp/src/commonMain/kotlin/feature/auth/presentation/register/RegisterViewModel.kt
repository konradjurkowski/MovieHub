package feature.auth.presentation.register

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.validator.FormValidator
import core.utils.constants.Constants
import core.utils.Platform
import core.utils.PlatformInfo
import core.utils.Resource
import feature.auth.data.remote.AuthService
import feature.auth.presentation.register.RegisterIntent.BackPressed
import feature.auth.presentation.register.RegisterIntent.NameChanged
import feature.auth.presentation.register.RegisterIntent.EmailChanged
import feature.auth.presentation.register.RegisterIntent.PasswordChanged
import feature.auth.presentation.register.RegisterIntent.RepeatedPasswordChanged
import feature.auth.presentation.register.RegisterIntent.TogglePasswordVisibility
import feature.auth.presentation.register.RegisterIntent.ToggleRepeatedPasswordVisibility
import feature.auth.presentation.register.RegisterIntent.SignUp
import feature.auth.presentation.register.RegisterSideEffect.GoToHome
import feature.auth.presentation.register.RegisterSideEffect.GoToNotificationPermission
import feature.auth.presentation.register.RegisterSideEffect.NavigateBack
import feature.auth.presentation.register.RegisterSideEffect.ShowError
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val formValidator: FormValidator,
    private val authService: AuthService,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<RegisterIntent, RegisterSideEffect, RegisterState>() {
    override fun getDefaultState(): RegisterState = RegisterState()

    override fun processIntent(intent: RegisterIntent) {
        when (intent) {
            BackPressed -> sendSideEffect(NavigateBack)
            is NameChanged -> updateViewState { copy(name = intent.name) }
            is EmailChanged -> updateViewState { copy(email = intent.email) }
            is PasswordChanged -> updateViewState { copy(password = intent.password) }
            is RepeatedPasswordChanged -> {
                updateViewState { copy(repeatedPassword = intent.repeatedPassword) }
            }
            TogglePasswordVisibility -> updateViewState { copy(obscurePassword = !obscurePassword) }
            ToggleRepeatedPasswordVisibility -> {
                updateViewState { copy(obscureRepeatedPassword = !obscureRepeatedPassword) }
            }
            is SignUp -> signUp(
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
        screenModelScope.launch(dispatchersProvider.io) {
            val result = authService.signUp(name, email, password)

            when (result) {
                is Resource.Success -> checkIfPermissionGranted()
                is Resource.Failure -> sendSideEffect(ShowError(result.error))
                else -> {
                    // NO - OP
                }
            }

            updateViewState { copy(registerState = result) }
        }
    }

    private fun checkIfPermissionGranted() {
        val platform = PlatformInfo.platform
        val sdkInt = PlatformInfo.sdkInt
        val requiresPermissionCheck = platform == Platform.IOS ||
                (platform == Platform.Android && sdkInt >= Constants.ANDROID_13_VERSION_CODE)

        if (requiresPermissionCheck) {
            sendSideEffect(GoToNotificationPermission)
            return
        }

        sendSideEffect(GoToHome)
    }
}
