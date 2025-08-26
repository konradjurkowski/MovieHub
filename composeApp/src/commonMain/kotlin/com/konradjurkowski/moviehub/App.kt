package com.konradjurkowski.moviehub

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.konradjurkowski.moviehub.core.navigation.AppNavigationGraph
import com.konradjurkowski.moviehub.core.presentation.theme.MovieHubTheme
import com.konradjurkowski.moviehub.core.utils.LocalNavController
import com.konradjurkowski.snackbarkmm.ContentWithSnackBar

@Composable
fun App() {
    MovieHubTheme {
        val navController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            CompositionLocalProvider(
                LocalNavController provides navController,
            ) {
                ContentWithSnackBar { AppNavigationGraph() }
            }
        }
    }
}
