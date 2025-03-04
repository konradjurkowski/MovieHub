package feature.auth.presentation.forgot_password

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.validator.FormValidator
import core.utils.Resource
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

        updateViewState { copy(resetState = Resource.Loading) }
        screenModelScope.launch(dispatchersProvider.io) {
            val result = authService.resetPassword(email)

            when (result) {
                is Resource.Success -> sendSideEffect(GoToLogin)
                is Resource.Failure -> sendSideEffect(ShowError(result.error))
                else -> {
                    // NO - OP
                }
            }

            updateViewState { copy(resetState = result) }
        }
    }
}
