package com.konradjurkowski.moviehub.core.presentation.comp.media.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.presentation.comp.button.BoxButton
import com.konradjurkowski.moviehub.core.presentation.comp.image.AnyImage
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.theme.withA70
import com.konradjurkowski.moviehub.core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_calendar
import moviehub.composeapp.generated.resources.ic_clock
import moviehub.composeapp.generated.resources.ic_ticket

@Composable
fun MediaDetailsInfo(
    modifier: Modifier = Modifier,
    title: String,
    releaseDate: String,
    duration: String,
    genre: String,
    posterUrl: String?,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.padding16),
    ) {
        BoxButton(modifier = Modifier.safeDrawingPadding()) { onBackClick() }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(140.dp),
                shape = RoundedCornerShape(Dimens.radius12),
                elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation),
            ) {
                AnyImage(modifier = Modifier.fillMaxSize(), image = posterUrl)
            }
            RegularSpacer()
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            RegularSpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MediaDetail(
                        modifier = Modifier.padding(end = Dimens.padding8),
                        text = releaseDate,
                        iconRes = Res.drawable.ic_calendar,
                    )
                    VerticalDivider(
                        modifier = Modifier.height(Dimens.padding16),
                        color = MaterialTheme.colorScheme.onBackground.withA70(),
                    )
                    MediaDetail(
                        modifier = Modifier.padding(horizontal = Dimens.padding8),
                        text = duration,
                        iconRes = Res.drawable.ic_clock,
                    )
                    VerticalDivider(
                        modifier = Modifier.height(Dimens.padding16),
                        color = MaterialTheme.colorScheme.onBackground.withA70(),
                    )
                    MediaDetail(
                        modifier = Modifier.padding(start = Dimens.padding8),
                        text = genre,
                        iconRes = Res.drawable.ic_ticket,
                    )
                }
            }
        }
    }
}
