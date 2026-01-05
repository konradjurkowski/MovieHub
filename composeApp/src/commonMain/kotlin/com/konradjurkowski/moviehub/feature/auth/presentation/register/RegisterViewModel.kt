package com.konradjurkowski.moviehub.feature.auth.presentation.register

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateBaseUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateEmailUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidatePasswordMatchUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidatePasswordUseCase
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.ActivateAccountRoute
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterEvent
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterEvent.ShowError
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.EmailChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.NameChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.PasswordChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.RegisterPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.ConfirmPasswordChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.TogglePasswordVisibility
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterIntent.ToggleConfirmPasswordVisibility
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterState
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val validateBase: ValidateBaseUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validatePasswordMatch: ValidatePasswordMatchUseCase,
    private val navigator: AppNavigator,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<RegisterIntent, RegisterState, RegisterEvent>(
    initialState = RegisterState(),
) {

    override fun processIntent(intent: RegisterIntent) {
        when (intent) {
            is NameChanged -> updateState { copy(name = intent.name) }
            is EmailChanged -> updateState { copy(email = intent.email) }
            is PasswordChanged -> updateState { copy(password = intent.password) }
            TogglePasswordVisibility -> updateState { copy(obscurePassword = !obscurePassword) }
            is ConfirmPasswordChanged -> updateState { copy(confirmPassword = intent.confirmPassword) }
            ToggleConfirmPasswordVisibility -> updateState { copy(obscureConfirmPassword = !obscureConfirmPassword) }
            is RegisterPressed -> register(
                name = intent.name,
                email = intent.email,
                password = intent.password,
                confirmPassword = intent.confirmPassword,
            )
        }
    }

    private fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        if (state.isLoading) return

        val nameValidation = validateBase(name)
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)
        val confirmPasswordValidation = validatePasswordMatch(password, confirmPassword)

        updateState {
            copy(
                nameValidation = nameValidation,
                emailValidation = emailValidation,
                passwordValidation = passwordValidation,
                confirmPasswordValidation = confirmPasswordValidation,
            )
        }

        if (!nameValidation.successful || !emailValidation.successful ||
            !passwordValidation.successful || !confirmPasswordValidation.successful) return

        updateState { copy(registerState = ActionState.Loading) }
        viewModelScope.launch(dispatchersProvider.io) {
            when (val result = authRepository.register(name, email, password)) {
                is Response.Success -> {
                    navigator.replace(ActivateAccountRoute(email = email))
                    updateState { copy(registerState = ActionState.Success) }
                }

                is Response.Failure -> {
                    sendEvent(ShowError(result.error))
                    updateState { copy(registerState = ActionState.Failure) }
                }
            }
        }
    }
}
