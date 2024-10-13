package core.components.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import core.components.image.AnyImage
import core.utils.Dimens
import core.utils.LocalTouchFeedback
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.user_placeholder

@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    size: Dp = Dimens.largeUserAvatar,
    imageUrl: String? = null,
) {
    Card(
        modifier = modifier
            .size(size),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation),
    ) {
        AnyImage(
            modifier = Modifier.fillMaxSize(),
            image = imageUrl,
            placeholderRes = Res.drawable.user_placeholder,
        )
    }
}

@Composable
fun EditableUserAvatar(
    modifier: Modifier = Modifier,
    image: Any?,
    onEditPressed: (() -> Unit)? = null,
) {
    val touchFeedback = LocalTouchFeedback.current
    Box(modifier = modifier) {
        Card(
            modifier = modifier.size(Dimens.largeUserAvatar),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation),
        ) {
            AnyImage(
                modifier = Modifier.fillMaxSize(),
                image = image,
                placeholderRes = Res.drawable.user_placeholder,
            )
        }
        onEditPressed?.let {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(Dimens.icon32)
                    .offset(x = Dimens.padding8, y = -Dimens.padding8),
                color = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        touchFeedback.performShortPress()
                        onEditPressed.invoke()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
