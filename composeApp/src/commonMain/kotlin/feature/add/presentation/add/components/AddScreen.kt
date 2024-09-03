package feature.add.presentation.add.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import core.components.other.RegularSpacer
import core.components.top_bar.LogoTopBar
import core.theme.withA20
import core.theme.withA50
import core.theme.withA60
import core.utils.Dimens
import core.utils.getScreenSizeInfo
import feature.add.presentation.add.AddScreenIntent
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.add_screen_add_movie_label
import moviehub.composeapp.generated.resources.add_screen_add_series_label
import moviehub.composeapp.generated.resources.movies_background
import moviehub.composeapp.generated.resources.series_background
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddScreen(
    onIntent: (AddScreenIntent) -> Unit,
) {
    val screenSize = getScreenSizeInfo()
    Scaffold(
        topBar = { LogoTopBar(isLeadingVisible = true) },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(screenSize.width * 0.1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AddBox(
                modifier = Modifier.weight(1f),
                title = stringResource(Res.string.add_screen_add_movie_label),
                backgroundRes = Res.drawable.movies_background,
                onClick = { onIntent(AddScreenIntent.AddMoviePressed) },
            )
            RegularSpacer()
            AddBox(
                modifier = Modifier.weight(1f),
                title = stringResource(Res.string.add_screen_add_series_label),
                backgroundRes = Res.drawable.series_background,
                onClick = { onIntent(AddScreenIntent.AddSeriesPressed) },
            )
        }
    }
}

@Composable
private fun AddBox(
    modifier: Modifier = Modifier,
    title: String,
    backgroundRes: DrawableResource,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(Dimens.radius10))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(Dimens.radius10),
        border = BorderStroke(
            width = Dimens.border1,
            color = MaterialTheme.colorScheme.onBackground.withA20(),
        ),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(backgroundRes),
            contentScale = ContentScale.Crop,
            contentDescription = "AddBox background",
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.withA60()),
        )
        Column(
            modifier = Modifier.padding(Dimens.padding16),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(Dimens.icon40)
                    .border(
                        width = Dimens.border2,
                        color = MaterialTheme.colorScheme.onBackground.withA50(),
                        shape = CircleShape,
                    )
                    .padding(Dimens.padding8),
                imageVector = Icons.Default.Add,
                contentDescription = "AddBox icon",
            )
            RegularSpacer()
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.ExtraLight,
            )
        }
    }
}
