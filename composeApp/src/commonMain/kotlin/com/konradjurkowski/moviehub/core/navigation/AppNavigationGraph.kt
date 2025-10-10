package com.konradjurkowski.moviehub.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.konradjurkowski.moviehub.core.utils.helpers.LocalNavController
import com.konradjurkowski.moviehub.core.navigation.CoreDestination.MainRoute
import com.konradjurkowski.moviehub.core.navigation.CoreDestination.QrCodeScannerRoute
import com.konradjurkowski.moviehub.core.presentation.screens.main.MainScreen
import com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.QrCodeScannerScreen
import com.konradjurkowski.moviehub.core.utils.extensions.CollectNavActions
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.SplashRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.addAuthGraph
import com.konradjurkowski.moviehub.feature.movies.navigation.addMoviesGraph
import org.koin.compose.koinInject

@Composable
fun AppNavigationGraph(navigator: AppNavigator = koinInject()) {
    val navController = LocalNavController.current
    CollectNavActions(navController, navigator.navActionFlow)

    val enterTransition = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(300),
    )
    val exitTransition = slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(300),
    )

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = SplashRoute,
        enterTransition = { enterTransition },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { exitTransition },
    ) {
        addCoreGraph()
        addAuthGraph()
        addMoviesGraph()
    }
}

fun NavGraphBuilder.addCoreGraph() {
    composable<QrCodeScannerRoute> { QrCodeScannerScreen() }
    composable<MainRoute> { MainScreen() }
}
