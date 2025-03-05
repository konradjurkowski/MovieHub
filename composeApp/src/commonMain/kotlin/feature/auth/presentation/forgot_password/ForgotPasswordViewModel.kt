package feature.auth.presentation.forgot_password

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.ActionState
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import core.tools.validator.FormValidator
import feature.auth.data.remote.AuthService
import kotlinx.coroutines.launch
import feature.auth.presentation.forgot_password.ForgotPasswordIntent.BackPressed
import feature.auth.presentation.forgot_password.ForgotPasswordIntent.EmailChanged
import feature.auth.presentation.forgot_password.ForgotPasswordIntent.ResetPassword
import feature.auth.presentation.forgot_password.ForgotPasswordSideEffect.GoToLogin
import feature.auth.presentation.forgot_password.ForgotPasswordSideEffect.NavigateBack
import feature.auth.presentation.forgot_password.ForgotPasswordSideEffect.ShowError

class ForgotPasswordViewModel(
    private val formValidator: FormValidator,
    private val authService: AuthService,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<ForgotPasswordIntent, ForgotPasswordSideEffect, ForgotPasswordState>() {
    override fun getDefaultState(): ForgotPasswordState = ForgotPasswordState()

    override fun processIntent(intent: ForgotPasswordIntent) {
        when (intent) {
            BackPressed -> sendSideEffect(NavigateBack)
            is EmailChanged -> updateViewState { copy(email = intent.email) }
            is ResetPassword -> resetPassword(intent.email)
        }
    }

    private fun resetPassword(email: String) {
        if (viewState.value.resetState.isLoading()) return

        val emailValidation = formValidator.validateEmail(email)
        updateViewState { copy(emailValidation = emailValidation) }

        if (!emailValidation.successful) return

        updateViewState { copy(resetState = ActionState.Loading) }
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = authService.sendPasswordResetEmail(email)) {
                is Response.Success -> {
                    updateViewState { copy(resetState = ActionState.Success) }
                    sendSideEffect(GoToLogin)
                }

                is Response.Failure -> {
                    updateViewState { copy(resetState = ActionState.Failure(result.error)) }
                    sendSideEffect(ShowError(result.error))
                }
            }
        }
    }
}
