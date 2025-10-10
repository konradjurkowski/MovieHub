package com.konradjurkowski.moviehub.core.utils.extensions

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.konradjurkowski.moviehub.core.domain.model.navigation.NavAction
import com.konradjurkowski.moviehub.core.domain.model.navigation.NavigationTab
import kotlinx.coroutines.flow.SharedFlow

fun NavController.canPop(): Boolean = previousBackStackEntry != null

fun <T : Any> NavController.replace(route: T) {
    popBackStack()
    navigate(route = route)
}

fun <T : Any> NavController.replaceAll(route: T) {
    navigate(route) {
        popUpTo(0) { inclusive = true }
    }
}

fun NavHostController.switchTab(tab: NavigationTab) {
    if (currentDestination?.route == tab.route) return

    navigate(tab.route) {
        launchSingleTop = true
        popUpTo(graph.startDestinationRoute ?: "") {
            saveState = true
        }
        restoreState = true
    }
}

inline fun <reified T : Any> NavGraphBuilder.staticComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        content = content
    )
}

@Composable
fun CollectNavActions(navController: NavHostController, navActions: SharedFlow<NavAction>) {
    val uriHandler = LocalUriHandler.current
    LaunchedEffect(Unit) {
        navActions.collect { action ->
            when (action) {
                is NavAction.Back -> navController.navigateUp()
                is NavAction.BackTo<*> -> navController.popBackStack(route = action.route, inclusive = false)
                is NavAction.Push<*> -> navController.navigate(action.route)
                is NavAction.Replace<*> -> navController.replace(action.route)
                is NavAction.ReplaceAll<*> -> navController.replaceAll(action.route)
                is NavAction.OpenUrl -> uriHandler.openUri(action.url)
            }
        }
    }
}
