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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import core.navigation.GlobalNavigators
import core.theme.withA10
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.onBackground.withA10(),
                        tonalElevation = 0.dp,
                    ) {
                        tabList.subList(0, 2).forEach { tab ->
                            NavigationItem(
                                tab = tab,
                                selected = it.current == tab,
                                onClick = { onIntent(MainScreenIntent.TabPressed(tab)) },
                            )
                        }

                        AddButton(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = Dimens.padding16),
                            onClick = { onIntent(MainScreenIntent.AddPressed) },
                        )

                        tabList.subList(2, tabList.size).forEach { tab ->
                            NavigationItem(
                                tab = tab,
                                selected = it.current == tab,
                                onClick = { onIntent(MainScreenIntent.TabPressed(tab)) },
                            )
                        }
                    }
                }
            }
        )
    }
}
