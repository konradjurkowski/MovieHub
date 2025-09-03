package com.konradjurkowski.moviehub.core.presentation.main.comp

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.konradjurkowski.moviehub.core.presentation.main.LocalTabNavigator
import com.konradjurkowski.moviehub.core.presentation.main.ise.MainIntent
import com.konradjurkowski.moviehub.core.presentation.main.ise.MainIntent.TabPressed
import com.konradjurkowski.moviehub.core.presentation.main.ise.MainState
import com.konradjurkowski.moviehub.core.presentation.theme.withA10
import com.konradjurkowski.moviehub.core.presentation.theme.withA20
import com.konradjurkowski.moviehub.core.utils.helpers.drawTopBorder

@Composable
fun MainContent(
    state: MainState,
    onIntent: (MainIntent) -> Unit,
) {
    val tabNavigator = LocalTabNavigator.current

    Scaffold(
        bottomBar = {
            val navBackStackEntry by tabNavigator.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawTopBorder(color = MaterialTheme.colorScheme.onBackground.withA20()),
                containerColor = MaterialTheme.colorScheme.onBackground.withA10(),
                tonalElevation = 0.dp,
                content = {
                    state.tabList.forEach { tab ->
                        NavigationItem(
                            tab = tab,
                            selected = tab.route == currentDestination?.route,
                            onClick = { onIntent(TabPressed(tab)) },
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .consumeWindowInsets(WindowInsets.navigationBars),
                navController = tabNavigator,
                startDestination = state.tabList.first().route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                state.tabList.forEach { tab ->
                    composable(tab.route) { tab.Content() }
                }
            }
        },
    )
}
