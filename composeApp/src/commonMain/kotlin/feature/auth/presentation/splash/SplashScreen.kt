package feature.auth.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.utils.getScreenSizeInfo
import feature.auth.presentation.login.LoginScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_icon
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

class SplashScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val screenSize = getScreenSizeInfo()

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                delay(1500)
                navigator.replace(LoginScreen())
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .width(screenSize.width * 0.7f),
                painter = painterResource(Res.drawable.ic_icon),
                contentDescription = "Splash logo"
            )
        }
    }
}
