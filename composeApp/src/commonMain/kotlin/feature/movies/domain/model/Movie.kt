package feature.movies.domain.model

import kotlinx.datetime.Instant

data class Movie(
    val id: Long,
    val title: String,
    val language: String,
    val adult: Boolean,
    val overview: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val genreIds: List<Long>,
    val popularity: Double,
    val releaseDate: Instant? = null,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long,
)
