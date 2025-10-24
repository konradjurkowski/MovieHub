package com.konradjurkowski.moviehub.feature.auth.presentation.welcome.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.konradjurkowski.moviehub.core.presentation.comp.button.PrimaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.button.SecondaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.other.LargeSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.helpers.rememberScreenSize
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.ise.WelcomeIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.ise.WelcomeIntent.LoginPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.welcome.ise.WelcomeIntent.RegisterPressed
import com.konradjurkowski.weatherapp.BuildKonfig
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_logo_splash
import moviehub.composeapp.generated.resources.welcome_screen_login_label
import moviehub.composeapp.generated.resources.welcome_screen_register_label
import moviehub.composeapp.generated.resources.welcome_screen_version_label
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WelcomeContent(onIntent: (WelcomeIntent) -> Unit) {
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
                contentDescription = "WelcomeLogo",
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(modifier = Modifier.padding(horizontal = Dimens.padding16)) {
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.welcome_screen_login_label),
                    backgroundColor = MaterialTheme.colorScheme.background,
                    foregroundColor = MaterialTheme.colorScheme.primary,
                    onClick = { onIntent(LoginPressed) },
                )
                RegularSpacer()
                SecondaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.welcome_screen_register_label),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    foregroundColor = MaterialTheme.colorScheme.background,
                    onClick = { onIntent(RegisterPressed) },
                )
                LargeSpacer()
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${stringResource(Res.string.welcome_screen_version_label)} ${BuildKonfig.VERSION_NAME}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background,
                )
                RegularSpacer()
            }
        }
    }
}
