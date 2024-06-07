package feature.movies.domain.model

import core.utils.Constants
import feature.movies.data.api.dto.MovieDto

data class Movie(
    val id: Long,
    val title: String,
    val language: String,
    val adult: Boolean,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
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
        posterPath = Constants.IMAGE_BASE_URL + posterPath,
        backdropPath = Constants.IMAGE_BASE_URL + backdropPath,
        genreIds = genreIds,
        popularity = popularity,
        releaseDate = releaseDate,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}
