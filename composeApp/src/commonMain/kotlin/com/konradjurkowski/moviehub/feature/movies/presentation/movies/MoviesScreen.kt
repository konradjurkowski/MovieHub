package com.konradjurkowski.moviehub.feature.movies.presentation.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.presentation.comp.button.AnimatedIconButton
import com.konradjurkowski.moviehub.core.presentation.comp.top_bar.MainTopBar
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.feature.movies.navigation.MoviesDestination.AddMovieRoute
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.movies_tab_label
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun MoviesScreen(appNavigator: AppNavigator = koinInject()) {
    Scaffold(
        topBar = {
            MainTopBar(
                title = stringResource(Res.string.movies_tab_label),
                actions = {
                    AnimatedIconButton(
                        modifier = Modifier.padding(horizontal = Dimens.padding16),
                        icon = Icons.Default.Add,
                        onClick = { appNavigator.push(AddMovieRoute) },
                    )
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text("Movies Screen")
        }
    }
}
