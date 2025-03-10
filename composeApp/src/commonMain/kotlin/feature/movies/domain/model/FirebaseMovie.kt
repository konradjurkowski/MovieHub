package feature.movies.domain.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseMovie(
    val movieId: Long,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val genreIds: List<Long>,
    val releaseDate: Instant? = null,
    val averageRating: Double = 0.0,
    val ratings: List<FirebaseRating> = emptyList(),
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)

fun Movie.toFirebaseMovie(
    createdAt: Instant? = null,
    updatedAt: Instant? = null,
): FirebaseMovie {
    return FirebaseMovie(
        movieId = id,
        title = title,
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

@Serializable
data class FirebaseRating(
    val userId: String,
    val rating: Double,
    val comment: String,
    val createdAt: Instant? = null,
)

fun List<FirebaseRating>.calculateAvgRating(): Double {
    if (this.isEmpty()) return 0.0
    return map { it.rating }.average()
}
