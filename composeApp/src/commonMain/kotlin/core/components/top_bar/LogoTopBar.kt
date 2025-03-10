package core.components.top_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.utils.getScreenSizeInfo
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LogoTopBar(
    modifier: Modifier = Modifier,
    isLeadingVisible: Boolean = false,
) {
    val deviceWidth = getScreenSizeInfo().width
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults
            .topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        title = {
            Image(
                modifier = Modifier.width(deviceWidth * 0.4f),
                painter = painterResource(Res.drawable.ic_logo),
                contentDescription = "TopBar logo",
            )
        },
        navigationIcon = {
            if (isLeadingVisible) NavigateBackArrow()
        },
    )
}
