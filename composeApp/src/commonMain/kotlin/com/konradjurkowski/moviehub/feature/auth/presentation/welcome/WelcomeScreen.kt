package com.konradjurkowski.moviehub.feature.auth.presentation.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.comp.WelcomeContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WelcomeScreen() {
    val viewModel = koinViewModel<WelcomeViewModel>()
    val state by viewModel.viewState.collectAsState()

    WelcomeContent(state = state, onIntent = viewModel::sendIntent)
}
