package feature.auth.presentation.forgot_password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.auth.presentation.forgot_password.components.ForgotPasswordScreen
import feature.auth.presentation.forgot_password.ForgotPasswordSideEffect.GoToLogin
import feature.auth.presentation.forgot_password.ForgotPasswordSideEffect.NavigateBack
import feature.auth.presentation.forgot_password.ForgotPasswordSideEffect.ShowError
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.forgot_password_screen_reset_password_success

class ForgotPasswordScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<ForgotPasswordViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                NavigateBack -> navigator.pop()
                is ShowError -> snackbarState.showError(getFailureMessage(effect.error))

                GoToLogin -> {
                    snackbarState.showSuccess(Res.string.forgot_password_screen_reset_password_success)
                    navigator.pop()
                }
            }
        }

        ForgotPasswordScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
