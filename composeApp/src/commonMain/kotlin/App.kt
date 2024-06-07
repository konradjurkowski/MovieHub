import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.konradjurkowski.snackbarkmp.ContentWithSnackBar
import com.konradjurkowski.snackbarkmp.SnackBarState
import com.konradjurkowski.snackbarkmp.rememberSnackBarState
import core.components.button.PrimaryButton
import core.theme.MovieHubTheme
import feature.auth.presentation.splash.SplashScreen
import feature.movies.presentation.movies.MoviesScreen
import feature.series.presentation.series.SeriesScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

val LocalSnackbarState =
    compositionLocalOf<SnackBarState> { error("No SnackbarHostState provided") }

@Composable
@Preview
fun App(isDarkTheme: Boolean) {
    MovieHubTheme(isDarkTheme) {
        val snackbarState = rememberSnackBarState()
        ContentWithSnackBar(snackBarState = snackbarState) {
            CompositionLocalProvider(LocalSnackbarState provides snackbarState) {
                Navigator(SplashScreen()) {
                    SlideTransition(it)
                }
//                Navigator(SeriesScreen()) {
//                    SlideTransition(it)
//                }
//                Navigator(MainScreen()) {
//                    SlideTransition(it)
//                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    Tab(
        modifier = Modifier.weight(1f),
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}

class MainScreen : Screen {
    @Composable
    override fun Content() {
        var isVisible by remember { mutableStateOf(true) }

        var homeTab = remember {
            MoviesTab(onNavigator = { isVisible = it })
        }

        TabNavigator(homeTab) {
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
                    AnimatedVisibility(visible = isVisible, enter = slideInVertically { height ->
                        height
                    }, exit = slideOutVertically { height ->
                        height
                    }) {
                        BottomAppBar {
                            TabNavigationItem(homeTab)
                            TabNavigationItem(SeriesTab)
                            TabNavigationItem(SearchTab)
                        }
                    }
                }
            )
        }
    }
}

class TestScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Box(modifier = Modifier.fillMaxSize()) {
            PrimaryButton(text = "TestScreen") {
                navigator.pop()
            }
        }
    }
}


fun Screen.isBottomBarVisible(): Boolean {
    // TODO ADD MORE
    return this is MoviesScreen || this is SeriesScreen
}
