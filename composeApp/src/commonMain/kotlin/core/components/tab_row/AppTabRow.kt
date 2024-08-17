package core.components.tab_row

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import core.theme.withA70
import core.utils.LocalTouchFeedback

@Composable
fun AppTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabPressed: (Int) -> Unit
) {
    val touchFeedback = LocalTouchFeedback.current
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        indicator = { },
        divider = { },
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    touchFeedback.performLongPress()
                    onTabPressed(index)
                },
                selectedContentColor = MaterialTheme.colorScheme.onBackground,
                unselectedContentColor = MaterialTheme.colorScheme.onBackground.withA70(),
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = when (selectedTabIndex == index) {
                            true -> FontWeight.SemiBold
                            false -> FontWeight.Light
                        }
                    )
                },
            )
        }
    }
}
