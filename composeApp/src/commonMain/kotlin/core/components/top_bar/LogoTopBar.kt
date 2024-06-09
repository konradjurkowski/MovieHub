package core.components.top_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.utils.getScreenSizeInfo
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_logo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun LogoTopBar(
    modifier: Modifier = Modifier,
    isLeadingVisible: Boolean = false,
) {
    val navigator = LocalNavigator.currentOrThrow
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
        navigationIcon = {
            if (isLeadingVisible) {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back Arrow",
                    )
                }
            }
        }
    )
}
