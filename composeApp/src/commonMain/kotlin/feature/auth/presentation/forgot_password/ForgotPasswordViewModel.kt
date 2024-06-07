package feature.auth.presentation.forgot_password

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.validator.FormValidator
import core.utils.Resource
import feature.auth.data.remote.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val formValidator: FormValidator,
    private val authService: AuthService,
) : BaseViewModel<ForgotPasswordIntent, ForgotPasswordSideEffect, ForgotPasswordState>() {
    override fun getDefaultState(): ForgotPasswordState = ForgotPasswordState()

    override fun processIntent(intent: ForgotPasswordIntent) {
        when (intent) {
            ForgotPasswordIntent.BackPressed -> sendSideEffect(ForgotPasswordSideEffect.NavigateBack)
            is ForgotPasswordIntent.EmailChanged -> updateViewState { copy(email = intent.email) }
            is ForgotPasswordIntent.ResetPassword -> resetPassword(intent.email)
        }
    }

    private fun resetPassword(email: String) {
        if (viewState.value.resetState.isLoading()) return

        val emailValidation = formValidator.validateEmail(email)
        updateViewState { copy(emailValidation = emailValidation) }

        if (!emailValidation.successful) return

        updateViewState { copy(resetState = Resource.Loading) }
        screenModelScope.launch(Dispatchers.IO) {
            val result = authService.resetPassword(email)

            when (result) {
                is Resource.Success -> sendSideEffect(ForgotPasswordSideEffect.GoToLogin)
                is Resource.Failure -> sendSideEffect(ForgotPasswordSideEffect.ShowError(result.error))
                else -> {
                    // NO - OP
                }
            }

            updateViewState { copy(resetState = result) }
        }
    }
}
