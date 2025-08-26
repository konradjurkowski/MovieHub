package com.konradjurkowski.moviehub.feature.auth.presentation.welcome

import androidx.compose.runtime.Composable
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.comp.WelcomeContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WelcomeScreen() {
    val viewModel = koinViewModel<WelcomeViewModel>()

    WelcomeContent(onIntent = viewModel::sendIntent)
}
