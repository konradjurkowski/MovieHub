package core.components.movie

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.utils.Dimens
import feature.movies.data.api.dto.Genre

@Composable
fun GenresListSection(
    modifier: Modifier = Modifier,
    genres: List<Genre>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        genres.forEachIndexed { index, genre ->
            SuggestionChip(
                modifier = Modifier
                    .padding(start = if (index == 0) 0.dp else Dimens.smallPadding),
                onClick = {},
                label = { Text(genre.name) },
                enabled = false,
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary),
                colors = SuggestionChipDefaults.suggestionChipColors(
                    disabledLabelColor = MaterialTheme.colorScheme.secondary,
                )
            )
        }
    }
}
