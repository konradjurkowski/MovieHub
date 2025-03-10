package feature.home.presentation.main

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import feature.home.presentation.tab.HomeTab
import feature.home.presentation.main.components.MainScreen
import feature.home.presentation.main.MainScreenSideEffect.SetTab
import feature.movies.presentation.tab.MoviesTab
import feature.profile.presentation.tab.ProfileTab
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

class MainScreenRoot : BaseScreen() {

    private val tabList = listOf(
        HomeTab,
        MoviesTab,
        SeriesTab,
        ProfileTab,
    )

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MainScreenViewModel>()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is SetTab -> GlobalNavigators.tabNavigator?.current = effect.tab
            }
        }

        MainScreen(
            tabList = tabList,
            onIntent = viewModel::sendIntent,
        )
    }
}

fun Tab.getIcon(selected: Boolean = false): DrawableResource {
    return when (this) {
        is HomeTab -> if (selected) Res.drawable.ic_home_selected else Res.drawable.ic_home
        is MoviesTab -> if (selected) Res.drawable.ic_movies_selected else Res.drawable.ic_movies
        is SeriesTab -> if (selected) Res.drawable.ic_series_selected else Res.drawable.ic_series
        else -> if (selected) Res.drawable.ic_profile_selected else Res.drawable.ic_profile
    }
}
