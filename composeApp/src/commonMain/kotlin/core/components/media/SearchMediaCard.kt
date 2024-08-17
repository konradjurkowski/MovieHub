package core.components.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.components.image.NetworkImage
import core.theme.withA70
import core.utils.Dimens

@Composable
fun SearchMediaCard(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String?,
) {
    Card(
        modifier = modifier
            .aspectRatio(3/4f)
            .padding(Dimens.padding8),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NetworkImage(
                modifier = Modifier.fillMaxSize(),
                imageUrl = imageUrl,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.withA70())
                    .padding(Dimens.padding8)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
