package feature.profile.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.profile_tab_label
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.profile_tab_label)

            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                )
            }
        }

    @Composable
    override fun Content() {
        ProfileScreen()
    }
}
