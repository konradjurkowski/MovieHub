package com.konradjurkowski.moviehub

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.konradjurkowski.moviehub.core.navigation.AppNavigationGraph
import com.konradjurkowski.moviehub.core.presentation.theme.MovieHubTheme
import com.konradjurkowski.moviehub.core.utils.helpers.LocalNavController
import com.konradjurkowski.snackbarkmm.ContentWithSnackBar

@Composable
fun App() {
    MovieHubTheme {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalNavController provides navController) {
            ContentWithSnackBar { AppNavigationGraph() }
        }
    }
}
