package com.konradjurkowski.moviehub.core.presentation.comp.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.konradjurkowski.moviehub.core.presentation.comp.button.SecondaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.presentation.theme.withA40
import com.konradjurkowski.moviehub.core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.error_label
import moviehub.composeapp.generated.resources.something_went_wrong_try_again
import moviehub.composeapp.generated.resources.try_again
import org.jetbrains.compose.resources.stringResource

@Composable
fun FailureWidget(
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.error_label),
    message: String = stringResource(Res.string.something_went_wrong_try_again),
    buttonText: String = stringResource(Res.string.try_again),
    onButtonClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.padding16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        SmallSpacer()
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground.withA40(),
            textAlign = TextAlign.Center,
        )
        RegularSpacer()
        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = buttonText,
            onClick = onButtonClick,
        )
    }
}
