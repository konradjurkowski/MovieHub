package feature.series.data.api.dto

import core.utils.Constants
import core.utils.toInstant
import feature.series.domain.model.Series
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesResponse (
    val results: List<SeriesDto>,
    val page: Long,
    @SerialName("total_pages")
    val totalPages: Long,
    @SerialName("total_results")
    val totalResults: Long,
)

@Serializable
data class SeriesDto (
    val id: Long,
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Long>,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String,
    val name: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Long,
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
        releaseDate = firstAirDate.toInstant(),
        voteAverage = voteAverage,
        voteCount = voteCount,
        adult = adult,
    )
}
