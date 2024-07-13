package core.components.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import core.components.image.NetworkImage
import core.utils.Dimens
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
        NetworkImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = imageUrl,
            placeholderRes = Res.drawable.user_placeholder,
        )
    }
}
