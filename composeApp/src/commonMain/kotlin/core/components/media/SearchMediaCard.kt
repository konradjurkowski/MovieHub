package core.components.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import core.components.button.AddButton
import core.components.image.AnyImage
import core.theme.withA50
import core.utils.Dimens
import core.utils.LocalTouchFeedback

@Composable
fun SearchMediaCard(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String?,
    isMediaAdded: Boolean,
    onAddClick: () -> Unit,
    onCardClick: () -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current

    Card(
        modifier = modifier
            .aspectRatio(3/4f)
            .padding(Dimens.padding8),
        onClick = {
            touchFeedback.performLongPress()
            onCardClick()
        },
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation),
        shape = RoundedCornerShape(Dimens.radius8),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AnyImage(
                modifier = Modifier.fillMaxSize(),
                image = imageUrl,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.withA50()),
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(Dimens.padding4),
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            AddButton(
                modifier = Modifier
                    .padding(Dimens.padding8)
                    .align(Alignment.TopEnd),
                isVisible = !isMediaAdded,
                onClick = onAddClick,
            )
        }
    }
}
