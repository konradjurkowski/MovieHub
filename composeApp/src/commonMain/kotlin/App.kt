import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.konradjurkowski.snackbarkmp.ContentWithSnackBar
import com.konradjurkowski.snackbarkmp.rememberSnackBarState
import core.theme.MovieHubTheme
import core.utils.LocalSnackbarState
import core.utils.LocalTouchFeedback
import core.utils.TouchFeedback
import feature.auth.presentation.splash.SplashScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    isDarkTheme: Boolean,
    touchFeedback: TouchFeedback,
) {
    MovieHubTheme(isDarkTheme) {
        val snackbarState = rememberSnackBarState()
        ContentWithSnackBar(snackBarState = snackbarState) {
            CompositionLocalProvider(
                LocalSnackbarState provides snackbarState,
                LocalTouchFeedback provides touchFeedback,
            ) {
                Navigator(SplashScreen()) {
                    SlideTransition(it)
                }
            }
        }
    }
}

//@Composable
//private fun RowScope.TabNavigationItem(tab: Tab) {
//    val tabNavigator = LocalTabNavigator.current
//
//    Tab(
//        modifier = Modifier.weight(1f),
//        selected = tabNavigator.current == tab,
//        onClick = { tabNavigator.current = tab },
//        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
//    )
//}

//class MainScreen : Screen {
//    @Composable
//    override fun Content() {
//        var isVisible by remember { mutableStateOf(true) }
//
//        var homeTab = remember {
//            MoviesTab(onNavigator = { isVisible = it })
//        }
//
//        TabNavigator(homeTab) {
//            Scaffold(
//                content = { contentPadding ->
//                    Box(
//                        modifier = Modifier
//                            // TODO HANDLE CONTENT PADDING
//                            .fillMaxSize()
//                    ) {
//                        CurrentTab()
//                    }
//                },
//                bottomBar = {
//                    AnimatedVisibility(visible = isVisible, enter = slideInVertically { height ->
//                        height
//                    }, exit = slideOutVertically { height ->
//                        height
//                    }) {
//                        BottomAppBar {
//                            TabNavigationItem(homeTab)
//                            TabNavigationItem(SeriesTab)
//                            TabNavigationItem(SearchTab)
//                        }
//                    }
//                }
//            )
//        }
//    }
//}

//class TestScreen : Screen {
//    @Composable
//    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
//        Box(modifier = Modifier.fillMaxSize()) {
//            PrimaryButton(text = "TestScreen") {
//                navigator.pop()
//            }
//        }
//    }
//}
//
//
//fun Screen.isBottomBarVisible(): Boolean {
//    // TODO ADD MORE
//    return this is MoviesScreen || this is SeriesScreen
//}
