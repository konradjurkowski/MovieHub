package feature.home.presentation.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import core.navigation.GlobalNavigators
import core.theme.withA10
import core.theme.withA20
import core.utils.Dimens
import feature.home.presentation.main.MainScreenIntent

@Composable
fun MainScreen(
    tabList: List<Tab>,
    onIntent: (MainScreenIntent) -> Unit,
) {
    TabNavigator(tabList.first()) {
        GlobalNavigators.tabNavigator = it
        Scaffold(
            content = { _ ->
                Box(modifier = Modifier
                    .padding(bottom = Dimens.navigationBarHeight)
                    .fillMaxSize(),
                ) {
                    CurrentTab()
                }
            },
            bottomBar = {
                val borderColor = MaterialTheme.colorScheme.onBackground.withA20()
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            drawLine(
                                color = borderColor,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = strokeWidth,
                            )
                        },
                    containerColor = MaterialTheme.colorScheme.onBackground.withA10(),
                    tonalElevation = 0.dp,
                ) {
                    tabList.forEach { tab ->
                        NavigationItem(
                            tab = tab,
                            selected = it.current == tab,
                            onClick = { onIntent(MainScreenIntent.TabPressed(tab)) },
                        )
                    }
                }
            }
        )
    }
}
