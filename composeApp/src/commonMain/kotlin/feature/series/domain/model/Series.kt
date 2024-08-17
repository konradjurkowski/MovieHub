package feature.series.domain.model

import core.utils.Constants
import feature.series.data.api.dto.SeriesDto

data class Series(
    val id: Long,
    val name: String,
    val overview: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val genreIds: List<Long>,
    val language: String,
    val popularity: Double,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Long,
    val adult: Boolean,
)

fun SeriesDto.toSeries(): Series {
    return Series(
        id = id,
        name = name,
        overview = overview,
        posterPath = Constants.IMAGE_BASE_URL + posterPath,
        backdropPath = Constants.IMAGE_BASE_URL + backdropPath,
        genreIds = genreIds,
        language = originalLanguage,
        popularity = popularity,
        releaseDate = firstAirDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        adult = adult,
    )
}
