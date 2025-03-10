package feature.movies.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import feature.movies.presentation.movies.MoviesScreenRoot
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.movies_tab_label
import org.jetbrains.compose.resources.stringResource

object MoviesTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.movies_tab_label)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(MoviesScreenRoot()) {
            SlideTransition(it)
        }
    }
}
