package feature.profile.presentation.profile_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.koin.getScreenModel
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.components.dialog.PermissionDialog
import core.navigation.GlobalNavigators
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import feature.profile.presentation.profile_edit.components.ProfileEditScreen
import feature.profile.presentation.profile_edit.ProfileEditIntent.DismissPermissionDialog
import feature.profile.presentation.profile_edit.ProfileEditIntent.GoToSettingsPressed
import feature.profile.presentation.profile_edit.ProfileEditIntent.ImageChanged
import feature.profile.presentation.profile_edit.ProfileEditSideEffect.OpenGallery
import feature.profile.presentation.profile_edit.ProfileEditSideEffect.ShowError
import feature.profile.presentation.profile_edit.ProfileEditSideEffect.ShowSuccessAndNavigateBack
import kotlinx.coroutines.launch
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.permission_gallery_permanently_denied
import moviehub.composeapp.generated.resources.profile_edit_screen_edit_success
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf

class ProfileEditScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val factory = rememberPermissionsControllerFactory()
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<ProfileEditViewModel> { parametersOf(factory.createPermissionsController()) }
        val state by viewModel.viewState.collectAsState()

        val coroutineScope = rememberCoroutineScope()
        val imagePicker = rememberImagePickerLauncher(
            selectionMode = SelectionMode.Single,
            scope = coroutineScope,
            onResult = {
                val byteArray = it.firstOrNull() ?: return@rememberImagePickerLauncher
                viewModel.sendIntent(ImageChanged(ByteArrayWrapper(byteArray)))
            },
        )

        BindEffect(viewModel.permissionsController)

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is ShowError -> snackbarState.showError(getFailureMessage(effect.error))
                OpenGallery -> coroutineScope.launch { imagePicker.launch() }

                ShowSuccessAndNavigateBack -> {
                    snackbarState.showSuccess(message = Res.string.profile_edit_screen_edit_success)
                    GlobalNavigators.navigator?.pop()
                }
            }
        }

        ProfileEditScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )

        if (state.showPermissionDialog) {
            PermissionDialog(
                message = stringResource(Res.string.permission_gallery_permanently_denied),
                onDismiss = {
                    viewModel.sendIntent(DismissPermissionDialog)
                },
                onGoToAppSettingsClick = {
                    viewModel.sendIntent(GoToSettingsPressed)
                },
            )
        }
    }
}
