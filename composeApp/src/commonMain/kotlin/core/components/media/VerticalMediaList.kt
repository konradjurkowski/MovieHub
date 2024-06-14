package core.components.media

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import feature.movies.domain.model.Movie
import feature.movies.presentation.movies.localMoviesGenresList
import feature.series.domain.model.Series
import feature.series.presentation.series.localSeriesGenresList

@Composable
fun <T> VerticalMediaList(
    mediaList: List<T> = emptyList(),
    isLoading: Boolean = false
) {
    if (!isLoading) {
        Column {
            mediaList.forEach { item ->
                if (item is Movie) {
                    VerticalMediaCard(
                        title = item.title,
                        imageUrl = item.posterPath ?: "",
                        rating = item.voteAverage,
                        genres = localMoviesGenresList.filter { item.genreIds.contains(it.id) }
                    )
                }
                if (item is Series) {
                    VerticalMediaCard(
                        title = item.name,
                        imageUrl = item.posterPath,
                        rating = item.voteAverage,
                        genres = localSeriesGenresList.filter { item.genreIds.contains(it.id) }
                    )
                }
            }
        }
    } else {
        Column {
            (0..20).forEach { _ ->
                VerticalMediaCardPlaceholder()
            }
        }
    }
}
