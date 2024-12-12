package core.components.media.cast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.text.SectionTitle
import core.utils.Dimens
import feature.movies.domain.model.Cast

@Composable
fun MediaCastList(
    modifier: Modifier = Modifier,
    castList: List<Cast>,
) {
    if (castList.isEmpty()) return

    Column(modifier = modifier) {
        HorizontalDivider(modifier = Modifier.padding(vertical = Dimens.padding16))
        SectionTitle(
            modifier = Modifier.padding(horizontal = Dimens.padding16),
            title = "Cast",
        )
        CastHorizontalList(
            modifier = Modifier.padding(top = Dimens.padding8),
            castList = castList,
        )
    }
}
