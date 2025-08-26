package com.konradjurkowski.moviehub.feature.auth.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.comp.SplashContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen() {
    val viewModel = koinViewModel<SplashViewModel>()
    val state by viewModel.viewState.collectAsState()

    SplashContent(state = state, onIntent = viewModel::sendIntent)
}
