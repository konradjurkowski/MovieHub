package com.konradjurkowski.moviehub.feature.auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateBaseUseCase
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateEmailUseCase
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.navigation.CoreDestination.MainRoute
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.core.utils.helpers.isNotificationPermissionGranted
import com.konradjurkowski.moviehub.feature.auth.domain.model.User
import com.konradjurkowski.moviehub.feature.auth.domain.model.isInAnyGroup
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.NotificationPermissionRoute
import com.konradjurkowski.moviehub.feature.auth.presentation.login.LoginEvent.ShowError
import com.konradjurkowski.moviehub.feature.auth.presentation.login.LoginIntent.EmailChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.login.LoginIntent.ForgotPasswordPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.login.LoginIntent.PasswordChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.login.LoginIntent.LoginPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.login.LoginIntent.TogglePasswordVisibility
import com.konradjurkowski.moviehub.feature.group.navigation.GroupDestination.JoinGroupRoute
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class LoginViewModel(
    val permissionsController: PermissionsController,
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
        if (state.loginState.isLoading()) return

        val emailValidation = validateEmail(email)
        val passwordValidation = validateBase(password)

        updateState { copy(emailValidation = emailValidation, passwordValidation = passwordValidation) }
        if (!emailValidation.successful || !passwordValidation.successful) return

        updateState { copy(loginState = ActionState.Loading) }
        viewModelScope.launch(dispatchersProvider.io) {
            when (val result = authRepository.login(email, password)) {
                is Response.Success -> {
                    navigateForward(user = result.data)
                    updateState { copy(loginState = ActionState.Success) }
                }

                is Response.Failure -> {
                    sendEvent(ShowError(result.error))
                    updateState { copy(loginState = ActionState.Failure) }
                }
            }
        }
    }

    private suspend fun navigateForward(user: User) {
        if (!user.isInAnyGroup()) {
            navigator.replaceAll(JoinGroupRoute)
            return
        }

        if (!permissionsController.isNotificationPermissionGranted()) {
            navigator.replaceAll(NotificationPermissionRoute)
            return
        }

        navigator.replaceAll(MainRoute)
    }
}
