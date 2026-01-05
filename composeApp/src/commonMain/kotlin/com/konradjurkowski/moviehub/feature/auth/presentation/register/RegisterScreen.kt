package com.konradjurkowski.moviehub.feature.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.core.utils.extensions.showError
import com.konradjurkowski.moviehub.feature.auth.presentation.register.ise.RegisterEvent.ShowError
import com.konradjurkowski.moviehub.feature.auth.presentation.register.comp.RegisterContent
import com.konradjurkowski.snackbarkmm.LocalSnackbarState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen() {
    val snackBarState = LocalSnackbarState.current

    val viewModel = koinViewModel<RegisterViewModel>()
    val state by viewModel.viewState.collectAsState()

    CollectEvents(viewModel.viewEvents) { event ->
        when (event) {
            is ShowError -> snackBarState.showError(event.error)
        }
    }

    RegisterContent(state = state, onIntent = viewModel::sendIntent)
}
