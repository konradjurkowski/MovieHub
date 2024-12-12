package feature.auth.presentation.splash.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.utils.getScreenSizeInfo
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen() {
    val screenSize = getScreenSizeInfo()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier
                .width(screenSize.width * 0.7f),
            painter = painterResource(Res.drawable.ic_logo),
            contentDescription = "Splash logo",
        )
    }
}
