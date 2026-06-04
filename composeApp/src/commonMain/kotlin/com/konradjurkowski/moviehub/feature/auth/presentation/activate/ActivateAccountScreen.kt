package com.konradjurkowski.moviehub.feature.auth.presentation.activate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.comp.ActivateAccountContent
import com.konradjurkowski.snackbarkmm.LocalSnackbarState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ActivateAccountScreen() {
    val factory = rememberPermissionsControllerFactory()
    val snackBarState = LocalSnackbarState.current

    val viewModel = koinViewModel<ActivateAccountViewModel> {
        parametersOf(factory.createPermissionsController())
    }
    val state by viewModel.viewState.collectAsState()

    BindEffect(viewModel.permissionsController)

    CollectEvents(viewModel.viewEvents) { event ->

    }

    ActivateAccountContent(state = state, onIntent = viewModel::sendIntent)
}
