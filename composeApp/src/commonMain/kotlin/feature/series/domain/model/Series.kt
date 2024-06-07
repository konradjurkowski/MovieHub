package feature.series.domain.model

import core.utils.Constants
import feature.series.data.api.dto.SeriesDto

data class Series(
    val id: Long,
    val name: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String? = null,
    val genreIds: List<Long>,
    val language: String,
    val popularity: Double,
    val firstAirDate: String,
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
        firstAirDate = firstAirDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        adult = adult
    )
}
