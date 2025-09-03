package com.konradjurkowski.moviehub.feature.group.presentation.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.core.domain.model.toImageData
import com.konradjurkowski.moviehub.core.presentation.comp.dialog.PermissionDialog
import com.konradjurkowski.moviehub.core.utils.helpers.rememberImagePicker
import com.konradjurkowski.moviehub.core.utils.helpers.showError
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupEvent.OpenGallery
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupEvent.ShowError
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.DismissPermissionDialog
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.OpenAppSettings
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.ImageChanged
import com.konradjurkowski.moviehub.feature.group.presentation.create.comp.CreateGroupContent
import com.konradjurkowski.snackbarkmm.LocalSnackbarState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.gallery_permission_settings_instructions
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CreateGroupScreen() {
    val scope = rememberCoroutineScope()
    val snackBarState = LocalSnackbarState.current
    val factory = rememberPermissionsControllerFactory()

    val viewModel = koinViewModel<CreateGroupViewModel> { parametersOf(factory.createPermissionsController()) }
    val state by viewModel.viewState.collectAsState()

    val imagePicker = rememberImagePicker {
        viewModel.sendIntent(ImageChanged(it.toImageData()))
    }

    BindEffect(viewModel.permissionsController)

    CollectEvents(viewModel.viewEvents) { event ->
        when (event) {
            is ShowError -> snackBarState.showError(event.error)
            OpenGallery -> scope.launch { imagePicker.launch() }
        }
    }

    CreateGroupContent(state = state, onIntent = viewModel::sendIntent)

    PermissionDialog(
        message = stringResource(Res.string.gallery_permission_settings_instructions),
        visible = state.showPermissionDialog,
        onDismiss = { viewModel.sendIntent(DismissPermissionDialog) },
        onGoToAppSettingsClick = { viewModel.sendIntent(OpenAppSettings) },
    )
}
