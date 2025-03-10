package core.components.media.watch_provider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.text.SectionTitle
import core.model.media.WatchProviderInfo
import core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.where_to_watch_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun MediaWatchProviderList(
    modifier: Modifier = Modifier,
    watchProviderList: List<WatchProviderInfo>,
) {
    if (watchProviderList.isEmpty()) return

    Column(modifier = modifier) {
        HorizontalDivider(modifier = Modifier.padding(vertical = Dimens.padding16))
        SectionTitle(
            modifier = Modifier.padding(horizontal = Dimens.padding16),
            title = stringResource(Res.string.where_to_watch_label),
        )
        WatchProviderHorizontalList(
            modifier = Modifier.padding(top = Dimens.padding8),
            watchProviderList = watchProviderList,
        )
    }
}
