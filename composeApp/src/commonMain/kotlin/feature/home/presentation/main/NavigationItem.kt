package feature.home.presentation.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.Tab
import core.utils.LocalTouchFeedback
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RowScope.NavigationItem(
    modifier: Modifier = Modifier,
    tab: Tab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current
    NavigationBarItem(
        modifier = modifier,
        selected = selected,
        onClick = {
            touchFeedback.performLongPress()
            onClick()
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = Color.Transparent,
        ),
        label = {
            Text(text = tab.options.title)
        },
        icon = {
            Icon(
                painter = when (selected) {
                    true -> painterResource(tab.getSelectedIcon())
                    false -> painterResource(tab.getIcon())
                },
                contentDescription = tab.options.title,
            )
        }
    )
}
