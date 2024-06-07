package feature.auth.presentation.forgot_password

import LocalSnackbarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.CollectSideEffects
import core.utils.getFailureMessage
import feature.auth.presentation.forgot_password.components.ForgotPasswordContent
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.reset_password_success_message
import org.jetbrains.compose.resources.ExperimentalResourceApi

class ForgotPasswordScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<ForgotPasswordViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                ForgotPasswordSideEffect.GoToLogin -> {
                    snackbarState.showSuccess(Res.string.reset_password_success_message)
                    navigator.pop()
                }
                ForgotPasswordSideEffect.NavigateBack -> navigator.pop()
                is ForgotPasswordSideEffect.ShowError -> {
                    snackbarState.showError(getFailureMessage(effect.error))
                }
            }
        }

        ForgotPasswordContent(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
