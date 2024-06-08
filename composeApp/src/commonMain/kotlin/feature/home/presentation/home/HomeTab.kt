package feature.home.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.home_tab_label
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.home_tab_label)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Home Tab",
            )
        }
    }
}
