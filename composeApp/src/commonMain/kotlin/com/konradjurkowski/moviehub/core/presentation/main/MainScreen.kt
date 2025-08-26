package com.konradjurkowski.moviehub.core.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.konradjurkowski.moviehub.core.architecture.CollectEvents
import com.konradjurkowski.moviehub.core.presentation.main.comp.MainContent
import com.konradjurkowski.moviehub.core.presentation.main.ise.MainEvent.SetTab
import com.konradjurkowski.moviehub.core.utils.extensions.switchTab
import org.koin.compose.viewmodel.koinViewModel

val LocalTabNavigator = compositionLocalOf<NavHostController> { error("No TabNavigator provided") }

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val tabNavigator = rememberNavController()

    CompositionLocalProvider(LocalTabNavigator provides tabNavigator) {
        val state by viewModel.viewState.collectAsState()

        CollectEvents(viewModel.viewEvents) { event ->
            when (event) {
                is SetTab -> tabNavigator.switchTab(event.tab)
            }
        }

        MainContent(
            state = state,
            onIntent = viewModel::sendIntent,
        )
    }
}
