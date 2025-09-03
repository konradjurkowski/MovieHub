package com.konradjurkowski.moviehub.feature.auth.presentation.register

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateBaseUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateEmailUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidatePasswordMatchUseCase
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.utils.tools.DispatchersProvider
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterEvent.ShowError
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterIntent.EmailChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterIntent.NameChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterIntent.PasswordChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterIntent.RegisterPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterIntent.ConfirmPasswordChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterIntent.TogglePasswordVisibility
import com.konradjurkowski.moviehub.feature.auth.presentation.register.RegisterIntent.ToggleConfirmPasswordVisibility
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val navigator: AppNavigator,
    private val validateBase: ValidateBaseUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidateBaseUseCase,
    private val validatePasswordMatch: ValidatePasswordMatchUseCase,
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
                confirmPassword = intent.confirmPassword
            )
        }
    }

    private fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        if (state.registerState.isLoading()) return

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
                    // TODO SHOW SUCCESS MESSAGE
                    navigator.back()
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
