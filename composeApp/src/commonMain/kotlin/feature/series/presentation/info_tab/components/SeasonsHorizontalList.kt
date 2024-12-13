package feature.series.presentation.info_tab.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.text.SectionTitle
import core.utils.Dimens
import core.utils.paddingForIndex
import feature.series.domain.model.Season
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.series_screen_details_seasons
import org.jetbrains.compose.resources.stringResource

@Composable
fun SeasonsHorizontalList(
    modifier: Modifier = Modifier,
    seasonList: List<Season>,
) {
    if (seasonList.isEmpty()) return

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(modifier = Modifier.padding(vertical = Dimens.padding16))
        SectionTitle(
            modifier = Modifier.padding(horizontal = Dimens.padding16),
            title = stringResource(Res.string.series_screen_details_seasons),
        )
        LazyRow(
            modifier = Modifier
                .padding(top = Dimens.padding8)
                .height(150.dp)
                .fillMaxWidth(),
        ) {
            itemsIndexed(seasonList) { index, season ->
                SeasonCard(
                    modifier = Modifier.paddingForIndex(index = index, size = seasonList.size),
                    season = season,
                )
            }
        }
    }
}
