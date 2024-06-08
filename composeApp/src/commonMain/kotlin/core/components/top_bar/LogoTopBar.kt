package core.components.top_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.utils.getScreenSizeInfo
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_logo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun LogoTopBar(
    modifier: Modifier = Modifier,
) {
    val deviceWidth = getScreenSizeInfo().width

    TopAppBar(
        modifier = modifier,
        title = {
            Image(
                modifier = Modifier.width(deviceWidth * 0.4f),
                painter = painterResource(Res.drawable.ic_logo),
                contentDescription = "TopBar logo",
            )
        },
    )
}
