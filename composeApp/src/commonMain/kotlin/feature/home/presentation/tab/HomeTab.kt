package feature.home.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import feature.home.presentation.home.HomeScreenRoot
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.home_tab_label
import org.jetbrains.compose.resources.stringResource

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
        Navigator(HomeScreenRoot()) {
            SlideTransition(it)
        }
    }
}
