package feature.profile.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import core.utils.safePush
import feature.auth.presentation.login.LoginScreenRoot
import feature.profile.presentation.profile.components.ProfileScreen
import feature.profile.presentation.profile_edit.ProfileEditScreenRoot

class ProfileScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ProfileViewModel>()
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                ProfileSideEffect.GoToLoginScreen -> GlobalNavigators.navigator?.replaceAll(LoginScreenRoot())
                ProfileSideEffect.GoToProfileEditScreen -> GlobalNavigators.navigator?.safePush(ProfileEditScreenRoot())
            }
        }

        ProfileScreen(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
