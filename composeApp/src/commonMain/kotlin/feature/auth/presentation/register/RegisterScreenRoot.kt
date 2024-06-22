package feature.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.CollectSideEffects
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.auth.presentation.register.components.RegisterScreen
import feature.home.presentation.main.MainScreenRoot
import org.jetbrains.compose.resources.ExperimentalResourceApi

class RegisterScreenRoot : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val snackbarState = LocalSnackbarState.current
        val viewModel = getScreenModel<RegisterViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                RegisterSideEffect.GoToHome -> navigator.replaceAll(MainScreenRoot())
                RegisterSideEffect.NavigateBack -> navigator.pop()
                is RegisterSideEffect.ShowError -> {
                    snackbarState.showError(getFailureMessage(effect.error))
                }
            }
        }

        RegisterScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
