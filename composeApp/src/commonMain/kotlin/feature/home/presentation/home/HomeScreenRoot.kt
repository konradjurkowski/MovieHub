package feature.home.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import feature.home.presentation.home.components.HomeScreen

class HomeScreenRoot : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeViewModel>()
        val state by viewModel.viewState.collectAsState()

        HomeScreen(
            state = state,
            onIntent = viewModel::sendIntent
        )
    }
}
