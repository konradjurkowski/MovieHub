package feature.profile.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.button.SecondaryButton
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.components.text.SectionTitle
import core.components.top_bar.LogoTopBar
import core.utils.Dimens
import feature.profile.presentation.profile.ProfileIntent
import feature.profile.presentation.profile.ProfileState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.profile_screen_about_label
import moviehub.composeapp.generated.resources.profile_screen_account_label
import moviehub.composeapp.generated.resources.profile_screen_change_password_label
import moviehub.composeapp.generated.resources.profile_screen_edit_user_data
import moviehub.composeapp.generated.resources.profile_screen_delete_my_account_label
import moviehub.composeapp.generated.resources.profile_screen_licenses_label
import moviehub.composeapp.generated.resources.profile_screen_logout_label
import moviehub.composeapp.generated.resources.profile_screen_report_problem_label
import moviehub.composeapp.generated.resources.profile_screen_support_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileScreen(
    state: ProfileState,
    onIntent: (ProfileIntent) -> Unit,
) {
    Scaffold(
        topBar = { LogoTopBar() },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(vertical = Dimens.padding16),
        ) {
            UserDataSection(user = state.appUser)
            RegularSpacer()
            SectionTitle(
                modifier = Modifier.padding(horizontal = Dimens.padding16),
                title = stringResource(Res.string.profile_screen_account_label),
            )
            SmallSpacer()
            SettingsItem(
                icon = Icons.Default.NoAccounts,
                title = stringResource(Res.string.profile_screen_delete_my_account_label),
                onClick = {
                    // TODO
                },
            )
            SmallSpacer()
            SettingsItem(
                icon = Icons.Default.AccountCircle,
                title = stringResource(Res.string.profile_screen_edit_user_data),
                onClick = {
                    onIntent(ProfileIntent.EditProfilePressed)
                },
            )
            SmallSpacer()
            SettingsItem(
                icon = Icons.Default.Key,
                title = stringResource(Res.string.profile_screen_change_password_label),
                onClick = {
                    // TODO
                },
            )
            RegularSpacer()
            SectionTitle(
                modifier = Modifier.padding(horizontal = Dimens.padding16),
                title = stringResource(Res.string.profile_screen_support_label),
            )
            SmallSpacer()
            SettingsItem(
                icon = Icons.Default.Info,
                title = stringResource(Res.string.profile_screen_about_label),
                onClick = {
                    // TODO
                },
            )
            SmallSpacer()
            SettingsItem(
                icon = Icons.AutoMirrored.Filled.Help,
                title = stringResource(Res.string.profile_screen_report_problem_label),
                onClick = {
                    // TODO
                },
            )
            SmallSpacer()
            SettingsItem(
                icon = Icons.Default.Lock,
                title = stringResource(Res.string.profile_screen_licenses_label),
                onClick = {
                    // TODO
                },
            )
            RegularSpacer()
            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.padding16),
                text = stringResource(Res.string.profile_screen_logout_label),
                onClick = { onIntent(ProfileIntent.LogoutPressed) },
            )
            RegularSpacer()
            AppVersionSection()
        }
    }
}
