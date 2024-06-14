package feature.movies.domain.model

import core.utils.Constants
import feature.movies.data.api.dto.MovieDto

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
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long
)

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        language = originalLanguage,
        adult = adult,
        overview = overview,
        posterPath = posterPath?.let { Constants.IMAGE_BASE_URL + it },
        backdropPath = backdropPath?.let { Constants.IMAGE_BASE_URL + it },
        genreIds = genreIds,
        popularity = popularity,
        releaseDate = releaseDate,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}
