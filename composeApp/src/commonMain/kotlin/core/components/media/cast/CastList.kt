package core.components.media.cast

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.utils.Dimens
import feature.movies.domain.model.Cast

@Composable
fun CastList(
    modifier: Modifier = Modifier,
    casts: List<Cast>,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.padding16),
        columns = GridCells.Fixed(2),
    ) {
        items(casts) { cast ->
            CastCard(cast = cast)
        }
    }
}
