package feature.permissions.presentation.notification

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import feature.home.presentation.main.MainScreenRoot
import feature.permissions.presentation.notification.NotificationPermissionSideEffect.GoToHomeScreen
import feature.permissions.presentation.notification.components.NotificationPermissionScreen
import org.koin.core.parameter.parameterSetOf

class NotificationPermissionScreenRoot : BaseScreen() {

    @Composable
    override fun Content() {
        val factory = rememberPermissionsControllerFactory()
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<NotificationPermissionViewModel> { parameterSetOf(factory.createPermissionsController()) }

        BindEffect(viewModel.permissionsController)

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                GoToHomeScreen -> navigator.replace(MainScreenRoot())
            }
        }

        NotificationPermissionScreen(onIntent = viewModel::sendIntent)
    }
}
