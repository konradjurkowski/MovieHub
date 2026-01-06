package com.konradjurkowski.moviehub.feature.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.core.utils.extensions.showError
import com.konradjurkowski.moviehub.feature.auth.presentation.login.ise.LoginEvent.ShowError
import com.konradjurkowski.moviehub.feature.auth.presentation.login.comp.LoginContent
import com.konradjurkowski.snackbarkmm.LocalSnackbarState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen() {
    val snackBarState = LocalSnackbarState.current

    val viewModel = koinViewModel<LoginViewModel>()
    val state by viewModel.viewState.collectAsState()

    CollectEvents(viewModel.viewEvents) { event ->
        when (event) {
            is ShowError -> snackBarState.showError(event.error)
        }
    }

    LoginContent(state = state, onIntent = viewModel::sendIntent)
}
