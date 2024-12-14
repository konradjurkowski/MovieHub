package feature.movies.data.api.dto

import core.utils.Constants
import core.utils.toInstant
import feature.movies.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val page: Long,
    val results: List<MovieDto>,
    @SerialName("total_pages")
    val totalPages: Long,
    @SerialName("total_results")
    val totalResults: Long,
)

@Serializable
data class MovieDto(
    val id: Long,
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Long>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Long,
)

fun MovieDto.toDomain(): Movie {
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
        releaseDate = releaseDate.toInstant(),
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}
