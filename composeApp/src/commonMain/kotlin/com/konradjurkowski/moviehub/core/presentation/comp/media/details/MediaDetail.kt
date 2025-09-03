package com.konradjurkowski.moviehub.core.presentation.comp.media.details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.konradjurkowski.moviehub.core.presentation.comp.other.TinySpacer
import com.konradjurkowski.moviehub.core.presentation.theme.withA70
import com.konradjurkowski.moviehub.core.utils.Dimens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MediaDetail(
    modifier: Modifier = Modifier,
    text: String,
    iconRes: DrawableResource,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(Dimens.icon16),
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.withA70(),
        )
        TinySpacer()
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground.withA70(),
        )
    }
}
