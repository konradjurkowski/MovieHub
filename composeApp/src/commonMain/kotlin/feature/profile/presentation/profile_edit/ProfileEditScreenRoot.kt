package feature.profile.presentation.profile_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.isGranted
import com.mohamedrejeb.calf.permissions.rememberPermissionState
import com.mohamedrejeb.calf.permissions.shouldShowRationale
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import core.architecture.CollectSideEffects
import core.components.dialog.PermissionDialog
import core.navigation.GlobalNavigators
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.profile.presentation.profile_edit.components.ProfileEditScreen
import kotlinx.coroutines.launch
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.permission_gallery_permanently_denied
import moviehub.composeapp.generated.resources.profile_edit_screen_edit_success
import org.jetbrains.compose.resources.stringResource

class ProfileEditScreenRoot : Screen {
    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current
        val viewModel = getScreenModel<ProfileEditViewModel>()
        val state by viewModel.viewState.collectAsState()

        val coroutineScope = rememberCoroutineScope()
        val galleryPermissionState = rememberPermissionState(Permission.Gallery)
        val imagePicker = rememberImagePickerLauncher(
            selectionMode = SelectionMode.Single,
            scope = coroutineScope,
            onResult = {
                val byteArray = it.firstOrNull() ?: return@rememberImagePickerLauncher
                viewModel.sendIntent(ProfileEditIntent.ImageChanged(ByteArrayWrapper(byteArray)))
            },
        )

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is ProfileEditSideEffect.ShowError -> {
                    snackbarState.showError(getFailureMessage(effect.error))
                }

                ProfileEditSideEffect.ShowSuccessAndNavigateBack -> {
                    snackbarState.showSuccess(message = Res.string.profile_edit_screen_edit_success)
                    GlobalNavigators.navigator?.pop()
                }

                ProfileEditSideEffect.OpenGalleryOrCheckPermission -> {
                    if (galleryPermissionState.status.isGranted) {
                        coroutineScope.launch { imagePicker.launch() }
                        return@CollectSideEffects
                    }

                    if (galleryPermissionState.status.shouldShowRationale) {
                        galleryPermissionState.launchPermissionRequest()
                        return@CollectSideEffects
                    }

                    viewModel.sendIntent(ProfileEditIntent.ShowPermissionDialog)
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
                    viewModel.sendIntent(ProfileEditIntent.DismissPermissionDialog)
                },
                onGoToAppSettingsClick = {
                    galleryPermissionState.openAppSettings()
                },
            )
        }
    }
}
