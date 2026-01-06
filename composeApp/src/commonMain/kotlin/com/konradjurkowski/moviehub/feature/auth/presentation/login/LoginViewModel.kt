package com.konradjurkowski.moviehub.feature.auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.data.api.dto.ApiErrorType
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateBaseUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateEmailUseCase
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.navigation.CoreDestination.MainRoute
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.core.utils.exceptions.ApiException
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.ActivateAccountRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.NotificationPermissionRoute
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginEvent
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginEvent.ShowError
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.EmailChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.ForgotPasswordPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.PasswordChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.LoginPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginIntent.TogglePasswordVisibility
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val navigator: AppNavigator,
    private val validateBase: ValidateBaseUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<LoginIntent, LoginState, LoginEvent>(
    initialState = LoginState(),
) {

    override fun processIntent(intent: LoginIntent) {
        when (intent) {
            is EmailChanged -> updateState { copy(email = intent.email) }
            is PasswordChanged -> updateState { copy(password = intent.password) }
            TogglePasswordVisibility -> updateState { copy(obscurePassword = !obscurePassword) }
            ForgotPasswordPressed -> { }
            is LoginPressed -> login(email = intent.email, password = intent.password)
        }
    }

    private fun login(email: String, password: String) {
        if (state.isLoading) return

        val emailValidation = validateEmail(email)
        val passwordValidation = validateBase(password)
        updateState { copy(emailValidation = emailValidation, passwordValidation = passwordValidation) }
        if (!emailValidation.successful || !passwordValidation.successful) return

        updateState { copy(loginState = ActionState.Loading) }
        viewModelScope.launch(dispatchersProvider.io) {
            when (val result = authRepository.login(email, password)) {
                is Response.Success -> {
                    navigateForward()
                    updateState { copy(loginState = ActionState.Success) }
                }

                is Response.Failure -> handleFailure(result.error)
            }
        }
    }

    private fun handleFailure(error: Throwable) {
        if (error is ApiException && error.code == ApiErrorType.ACCOUNT_NOT_ACTIVATED) {
            navigator.replace(ActivateAccountRoute(email = state.email))
            return
        }

        sendEvent(ShowError(error))
        updateState { copy(loginState = ActionState.Failure) }
    }

    private suspend fun navigateForward() {
        if (authRepository.isInitialLaunch()) {
            navigator.replaceAll(NotificationPermissionRoute)
            return
        }

        navigator.replaceAll(MainRoute)
        return
    }
}
