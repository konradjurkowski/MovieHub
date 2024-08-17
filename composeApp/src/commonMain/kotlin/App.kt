import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import com.konradjurkowski.snackbarkmp.ContentWithSnackBar
import com.konradjurkowski.snackbarkmp.rememberSnackBarState
import core.components.loading.ContentWithLoader
import core.components.loading.rememberLoaderState
import core.navigation.GlobalNavigators
import core.theme.MovieHubTheme
import core.tools.datetime_formatter.DateTimeFormatterImpl
import core.utils.LocalDateTimeFormatter
import core.utils.LocalLoaderState
import core.utils.LocalSnackbarState
import core.utils.LocalTouchFeedback
import core.utils.TouchFeedback
import feature.auth.presentation.splash.SplashScreenRoot

@Composable
fun App(
    isDarkTheme: Boolean,
    touchFeedback: TouchFeedback,
) {
    MovieHubTheme(isDarkTheme) {
        val snackbarState = rememberSnackBarState()
        val loaderState = rememberLoaderState()
        ContentWithSnackBar(snackBarState = snackbarState) {
            ContentWithLoader(loaderState = loaderState) {
                CompositionLocalProvider(
                    LocalSnackbarState provides snackbarState,
                    LocalLoaderState provides loaderState,
                    LocalTouchFeedback provides touchFeedback,
                    LocalDateTimeFormatter provides DateTimeFormatterImpl(),
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        Navigator(
                            screen = SplashScreenRoot(),
                            disposeBehavior = NavigatorDisposeBehavior(disposeNestedNavigators = false),
                            onBackPressed = {
                                loaderState.hideLoader()
                                true
                            }
                        ) {
                            GlobalNavigators.navigator = it
                            SlideTransition(it)
                        }
                    }
                }
            }
        }
    }
}
