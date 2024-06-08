package feature.series.presentation.series

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
import moviehub.composeapp.generated.resources.series_tab_label
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
object SeriesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.series_tab_label)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Series Tab",
            )
        }
    }
}
