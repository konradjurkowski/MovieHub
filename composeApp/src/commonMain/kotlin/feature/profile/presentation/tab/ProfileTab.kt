package feature.profile.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import feature.profile.presentation.profile.ProfileScreenRoot
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.profile_tab_label
import org.jetbrains.compose.resources.stringResource

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.profile_tab_label)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(ProfileScreenRoot()) {
            SlideTransition(it)
        }
    }
}
