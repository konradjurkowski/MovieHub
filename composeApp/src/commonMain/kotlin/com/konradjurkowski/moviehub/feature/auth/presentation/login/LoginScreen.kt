package com.konradjurkowski.moviehub.feature.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.core.utils.extensions.showError
import com.konradjurkowski.moviehub.feature.auth.presentation.login.LoginEvent.ShowError
import com.konradjurkowski.moviehub.feature.auth.presentation.login.comp.LoginContent
import com.konradjurkowski.snackbarkmm.LocalSnackbarState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LoginScreen() {
    val snackBarState = LocalSnackbarState.current
    val factory = rememberPermissionsControllerFactory()

    val viewModel = koinViewModel<LoginViewModel> { parametersOf(factory.createPermissionsController()) }
    val state by viewModel.viewState.collectAsState()

    BindEffect(viewModel.permissionsController)

    CollectEvents(viewModel.viewEvents) { event ->
        when (event) {
            is ShowError -> snackBarState.showError(event.error)
        }
    }

    LoginContent(state = state, onIntent = viewModel::sendIntent)
}
