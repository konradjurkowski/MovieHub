package feature.series.domain.model

import kotlinx.datetime.Instant

data class Series(
    val id: Long,
    val name: String,
    val overview: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val genreIds: List<Long>,
    val language: String,
    val popularity: Double,
    val releaseDate: Instant?,
    val voteAverage: Double,
    val voteCount: Long,
    val adult: Boolean,
)
