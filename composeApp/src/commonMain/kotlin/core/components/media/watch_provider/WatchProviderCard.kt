package core.components.media.watch_provider

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.image.AnyImage
import core.model.media.WatchProviderInfo
import core.model.media.getImageUrl
import core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.user_placeholder

@Composable
fun WatchProviderCard(
    modifier: Modifier = Modifier,
    provider: WatchProviderInfo,
) {
    Card(
        modifier = modifier.size(75.dp),
        shape = RoundedCornerShape(Dimens.radius16),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AnyImage(
                modifier = Modifier.fillMaxSize(),
                image = provider.getImageUrl(),
                placeholderRes = Res.drawable.user_placeholder,
            )
        }
    }
}
