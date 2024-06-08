package feature.home.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import core.utils.Dimens
import feature.home.presentation.home.HomeTab
import feature.movies.presentation.movies.MoviesTab
import feature.profile.presentation.profile.ProfileTab
import feature.series.presentation.series.SeriesTab
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
import org.jetbrains.compose.resources.ExperimentalResourceApi

class MainScreen : Screen {
    private val tabList = listOf(
        HomeTab,
        MoviesTab,
        SeriesTab,
        ProfileTab,
    )

    @Composable
    override fun Content() {
        TabNavigator(HomeTab) {
            Scaffold(
                content = { contentPadding ->
                    Box(
                        modifier = Modifier
                            // TODO HANDLE CONTENT PADDING
                            .fillMaxSize()
                    ) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        NavigationBar(
                            modifier = Modifier
                                .shadow(
                                    elevation = Dimens.defaultElevation,
                                    ambientColor = MaterialTheme.colorScheme.onBackground,
                                    spotColor = MaterialTheme.colorScheme.onBackground,
                                ),
                            containerColor = MaterialTheme.colorScheme.background,
                            tonalElevation = 0.dp,
                        ) {
                            val tabNavigator = LocalTabNavigator.current

                            tabList.subList(0, 2).forEach { tab ->
                                NavigationItem(
                                    tab = tab,
                                    selected = tabNavigator.current == tab,
                                    onClick = { tabNavigator.current = tab }
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            tabList.subList(2, tabList.size).forEach { tab ->
                                NavigationItem(
                                    tab = tab,
                                    selected = tabNavigator.current == tab,
                                    onClick = { tabNavigator.current = tab }
                                )
                            }
                        }

                        AddFAB(
                            modifier = Modifier
                                .align(Alignment.TopCenter),
                        ) {
                            // TODO Navigate to Add Screen
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
fun Tab.getIcon(): DrawableResource {
    return when (this) {
        is HomeTab -> Res.drawable.ic_home
        is MoviesTab -> Res.drawable.ic_movies
        is SeriesTab -> Res.drawable.ic_series
        else -> Res.drawable.ic_profile
    }
}

@OptIn(ExperimentalResourceApi::class)
fun Tab.getSelectedIcon(): DrawableResource {
    return when (this) {
        is HomeTab -> Res.drawable.ic_home_selected
        is MoviesTab -> Res.drawable.ic_movies_selected
        is SeriesTab -> Res.drawable.ic_series_selected
        else -> Res.drawable.ic_profile_selected
    }
}
