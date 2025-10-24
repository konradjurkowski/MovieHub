package com.konradjurkowski.moviehub.feature.auth.presentation.splash.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.core.presentation.comp.button.SecondaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.loading.LoadingIndicator
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.helpers.rememberScreenSize
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.ise.SplashIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.ise.SplashIntent.TryAgainPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.ise.SplashState
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.ise.SplashState.Error
import com.konradjurkowski.moviehub.feature.auth.presentation.splash.ise.SplashState.Loading
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_logo_splash
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashContent(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit,
) {
    val screenSize = rememberScreenSize()

    Scaffold(containerColor = MaterialTheme.colorScheme.primary) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .padding(top = screenSize.height * 0.2f)
                    .width(screenSize.width * 0.7f),
                painter = painterResource(Res.drawable.ic_logo_splash),
                contentDescription = "SplashLogo",
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(Dimens.padding16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                when (state) {
                    is Loading -> LoadingIndicator(color = MaterialTheme.colorScheme.background)
                    is Error -> {
                        Text(text = "Unable to sync data. Please try again.")
                        RegularSpacer()
                        SecondaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Try Again",
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            foregroundColor = MaterialTheme.colorScheme.background,
                            onClick = { onIntent(TryAgainPressed) },
                        )
                    }
                }
            }
        }
    }
}
