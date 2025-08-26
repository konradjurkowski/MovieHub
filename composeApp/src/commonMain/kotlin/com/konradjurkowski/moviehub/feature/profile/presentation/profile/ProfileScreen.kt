package com.konradjurkowski.moviehub.feature.profile.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.konradjurkowski.moviehub.feature.profile.presentation.profile.comp.ProfileContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel()) {
    val state by viewModel.viewState.collectAsState()

    ProfileContent(
        state = state,
        onIntent = viewModel::sendIntent,
    )
}
