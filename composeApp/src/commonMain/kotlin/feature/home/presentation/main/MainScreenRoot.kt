package feature.home.presentation.main

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import feature.add.presentation.add.AddScreenRoot
import feature.home.presentation.tab.HomeTab
import feature.home.presentation.main.components.MainScreen
import feature.movies.presentation.tab.MoviesTab
import feature.profile.presentation.profile.ProfileTab
import feature.series.presentation.tab.SeriesTab
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_home
import moviehub.composeapp.generated.resources.ic_home_selected
import moviehub.composeapp.generated.resources.ic_movies
import moviehub.composeapp.generated.resources.ic_movies_selected
import moviehub.composeapp.generated.resources.ic_profile
import moviehub.composeapp.generated.resources.ic_profile_selected
import moviehub.composeapp.generated.resources.ic_series
import moviehub.composeapp.generated.resources.ic_series_selected
import org.jetbrains.compose.resources.DrawableResource

class MainScreenRoot : Screen {
    private val tabList = listOf(
        HomeTab,
        MoviesTab,
        SeriesTab,
        ProfileTab,
    )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<MainScreenViewModel>()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                MainScreenSideEffect.GoToAdd -> navigator.push(AddScreenRoot())
                is MainScreenSideEffect.SetTab -> {
                    GlobalNavigators.tabNavigator?.current = effect.tab
                }
            }
        }

        MainScreen(
            tabList = tabList,
            onIntent = viewModel::sendIntent,
        )
    }
}

fun Tab.getIcon(): DrawableResource {
    return when (this) {
        is HomeTab -> Res.drawable.ic_home
        is MoviesTab -> Res.drawable.ic_movies
        is SeriesTab -> Res.drawable.ic_series
        else -> Res.drawable.ic_profile
    }
}

fun Tab.getSelectedIcon(): DrawableResource {
    return when (this) {
        is HomeTab -> Res.drawable.ic_home_selected
        is MoviesTab -> Res.drawable.ic_movies_selected
        is SeriesTab -> Res.drawable.ic_series_selected
        else -> Res.drawable.ic_profile_selected
    }
}
