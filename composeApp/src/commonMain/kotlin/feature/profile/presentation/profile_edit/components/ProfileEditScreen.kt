package feature.profile.presentation.profile_edit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.preat.peekaboo.image.picker.toImageBitmap
import core.components.button.PrimaryButton
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.components.text_field.InputTextField
import core.components.text_field.InvalidFieldMessage
import core.components.text_field.TextFieldLabel
import core.components.top_bar.LogoTopBar
import core.components.user.EditableUserAvatar
import core.utils.Dimens
import core.utils.clearFocus
import core.utils.toDisplay
import feature.profile.presentation.profile_edit.ProfileEditIntent
import feature.profile.presentation.profile_edit.ProfileEditState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.profile_edit_screen_description_label
import moviehub.composeapp.generated.resources.profile_edit_screen_email_label
import moviehub.composeapp.generated.resources.profile_edit_screen_name_label
import moviehub.composeapp.generated.resources.profile_edit_screen_save_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileEditScreen(
    state: ProfileEditState,
    onIntent: (ProfileEditIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.clearFocus(),
        topBar = {
            LogoTopBar(isLeadingVisible = true)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(vertical = Dimens.padding16),
        ) {
            Column(modifier = Modifier.padding(horizontal = Dimens.padding16)) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    EditableUserAvatar(
                        image = state.image?.array?.toImageBitmap() ?: state.appUser?.imageUrl,
                        onEditPressed = {
                            onIntent(ProfileEditIntent.OnEditImagePressed)
                        },
                    )
                }
                RegularSpacer()
                TextFieldLabel(text = stringResource(Res.string.profile_edit_screen_name_label))
                SmallSpacer()
                InputTextField(
                    value = state.name,
                    isError = state.editState.isFailure() || state.nameError != null,
                    imeAction = ImeAction.Next,
                    onValueChange = { onIntent(ProfileEditIntent.NameChanged(it)) },
                )
                InvalidFieldMessage(
                    message = state.nameError.toDisplay(),
                    isInvalid = state.nameError != null,
                )
                RegularSpacer()
                TextFieldLabel(text = stringResource(Res.string.profile_edit_screen_description_label))
                SmallSpacer()
                InputTextField(
                    value = state.description,
                    isError = state.editState.isFailure(),
                    onValueChange = { onIntent(ProfileEditIntent.DescriptionChanged(it)) },
                )
                RegularSpacer()
                TextFieldLabel(text = stringResource(Res.string.profile_edit_screen_email_label))
                SmallSpacer()
                InputTextField(
                    value = state.appUser?.email ?: "",
                    readOnly = true,
                    enabled = false,
                    onValueChange = {
                        // NO - OP
                    },
                )
                RegularSpacer()
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.profile_edit_screen_save_button),
                    loading = state.editState.isLoading(),
                    onClick = {
                        focusManager.clearFocus()
                        onIntent(
                            ProfileEditIntent.SavePressed(
                                state.name,
                                state.description,
                                state.image,
                            )
                        )
                    },
                )
            }
        }
    }
}
