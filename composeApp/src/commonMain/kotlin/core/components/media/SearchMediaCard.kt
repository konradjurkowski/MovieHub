package core.components.media

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
                isMediaAdded = isMediaAdded,
                onClick = onAddClick,
            )
        }
    }
}

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    isMediaAdded: Boolean,
    onClick: () -> Unit = {},
) {
    val touchFeedback = LocalTouchFeedback.current

    AnimatedVisibility(
        modifier = modifier,
        visible = !isMediaAdded,
        enter = slideInHorizontally(initialOffsetX = { - (it * 2) } ),
        exit = slideOutHorizontally(targetOffsetX = { it * 2 } ),
    ) {
        OutlinedIconButton(
            modifier = Modifier.size(30.dp),
            shape = RoundedCornerShape(Dimens.padding4),
            enabled = !isMediaAdded,
            onClick = {
                touchFeedback.performLongPress()
                onClick()
            },
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
            colors = IconButtonDefaults.outlinedIconButtonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Icon",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
