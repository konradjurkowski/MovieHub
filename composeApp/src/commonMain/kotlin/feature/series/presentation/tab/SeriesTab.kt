package feature.series.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import feature.series.presentation.series.SeriesScreenRoot
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.series_tab_label
import org.jetbrains.compose.resources.stringResource

object SeriesTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.series_tab_label)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(SeriesScreenRoot()) {
            SlideTransition(it)
        }
    }
}
