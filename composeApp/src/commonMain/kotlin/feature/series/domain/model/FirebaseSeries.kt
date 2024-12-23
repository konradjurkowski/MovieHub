package feature.series.domain.model

import feature.movies.domain.model.FirebaseRating
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseSeries(
    val seriesId: Long,
    val name: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val genreIds: List<Long>,
    val releaseDate: Instant?,
    val averageRating: Double = 0.0,
    val ratings: List<FirebaseRating> = emptyList(),
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)

fun Series.toFirebaseSeries(
    createdAt: Instant? = null,
    updatedAt: Instant? = null,
): FirebaseSeries {
    return FirebaseSeries(
        seriesId = id,
        name = name,
        overview = overview,
        language = language,
        adult = adult,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genreIds = genreIds,
        releaseDate = releaseDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
