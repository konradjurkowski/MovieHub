package feature.add.presentation.add

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.architecture.CollectSideEffects
import feature.add.presentation.add.components.AddScreen

class AddScreenRoot : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = getScreenModel<AddScreenViewModel>()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                AddScreenSideEffect.GoToAddMovie -> {}
                AddScreenSideEffect.GoToAddSeries -> {}
            }
        }

        AddScreen(onIntent = viewModel::sendIntent)
    }
}
