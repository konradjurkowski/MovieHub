package com.konradjurkowski.moviehub.feature.group.presentation.join

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.core.presentation.comp.dialog.PermissionDialog
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.DismissPermissionDialog
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.OpenAppSettings
import com.konradjurkowski.moviehub.feature.group.presentation.join.components.JoinGroupContent
import com.konradjurkowski.snackbarkmm.LocalSnackbarState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.camera_permission_settings_instructions
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun JoinGroupScreen() {
    val factory = rememberPermissionsControllerFactory()
    val snackBarState = LocalSnackbarState.current

    val viewModel = koinViewModel<JoinGroupViewModel> { parametersOf(factory.createPermissionsController()) }
    val state by viewModel.viewState.collectAsState()

    BindEffect(viewModel.permissionsController)

    CollectEvents(viewModel.viewEvents) { event ->
        // TODO
    }

    JoinGroupContent(
        state = state,
        onIntent = viewModel::sendIntent,
    )

    PermissionDialog(
        visible = state.showPermissionDialog,
        message = stringResource(Res.string.camera_permission_settings_instructions),
        onDismiss = { viewModel.sendIntent(DismissPermissionDialog) },
        onGoToAppSettingsClick = { viewModel.sendIntent(OpenAppSettings) },
    )
}
