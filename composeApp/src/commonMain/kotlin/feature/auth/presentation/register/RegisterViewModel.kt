package feature.auth.presentation.register

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.ActionState
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import core.tools.validator.FormValidator
import core.utils.isNotificationPermissionRequired
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
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
    val permissionsController: PermissionsController,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<RegisterIntent, RegisterSideEffect, RegisterState>() {
    override fun getDefaultState(): RegisterState = RegisterState()

    override fun processIntent(intent: RegisterIntent) {
        when (intent) {
            BackPressed -> sendSideEffect(NavigateBack)
            is NameChanged -> updateViewState { copy(name = intent.name) }
            is EmailChanged -> updateViewState { copy(email = intent.email) }
            is PasswordChanged -> updateViewState { copy(password = intent.password) }
            TogglePasswordVisibility -> updateViewState { copy(obscurePassword = !obscurePassword) }

            is RepeatedPasswordChanged -> {
                updateViewState { copy(repeatedPassword = intent.repeatedPassword) }
            }

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

        updateViewState { copy(registerState = ActionState.Loading) }
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = authService.signUp(name, email, password)) {
                is Response.Success -> {
                    updateViewState { copy(registerState = ActionState.Success) }
                    val isNotificationPermissionGranted = permissionsController.isPermissionGranted(Permission.REMOTE_NOTIFICATION)
                    if (!isNotificationPermissionRequired() || isNotificationPermissionGranted) {
                        sendSideEffect(GoToHome)
                        return@launch
                    }

                    sendSideEffect(GoToNotificationPermission)
                }

                is Response.Failure -> {
                    updateViewState { copy(registerState = ActionState.Failure(result.error)) }
                    sendSideEffect(ShowError(result.error))
                }
            }
        }
    }
}
