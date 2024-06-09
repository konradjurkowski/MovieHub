package feature.add.presentation.add.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.button.PrimaryButton
import core.components.other.RegularSpacer
import core.components.top_bar.LogoTopBar
import core.utils.Dimens
import feature.add.presentation.add.AddScreenIntent
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.add_screen_add_movie_label
import moviehub.composeapp.generated.resources.add_screen_add_series_label
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddScreen(
    onIntent: (AddScreenIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            LogoTopBar(isLeadingVisible = true)
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(Dimens.regularPadding),
            verticalArrangement = Arrangement.Center,
        ) {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.add_screen_add_movie_label),
            ) {
                onIntent(AddScreenIntent.AddMoviePressed)
            }
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.add_screen_add_series_label),
            ) {
                onIntent(AddScreenIntent.AddSeriesPressed)
            }
        }
    }
}
