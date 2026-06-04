package com.konradjurkowski.moviehub.feature.home.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    authRepository: AuthRepository = koinInject(),
) {
    LaunchedEffect(Unit) {
        authRepository.setFirstLaunchCompleted()
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text("Home Screen")
        }
    }
}
