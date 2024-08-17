package feature.movies.data.api.dto

import core.model.Genre
import core.model.ProductionCountry
import core.model.SpokenLanguage
import core.model.dto.ProductionCompanyDto
import core.model.dto.toDomain
import core.utils.Constants
import core.utils.toInstant
import feature.movies.domain.model.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto (
    val id: Long,
    val adult: Boolean,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val status: String,
    val tagline: String,
    val title: String,
    val popularity: Double,
    @SerialName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    @SerialName("imdb_id")
    val imdbId: String,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Long,
)

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath?.let { Constants.IMAGE_BASE_URL + it },
        backdropPath = backdropPath?.let { Constants.IMAGE_BASE_URL + it },
        budget = budget,
        genres = genres,
        tagline = tagline,
        status = status,
        popularity = popularity,
        releaseDate = releaseDate.toInstant(),
        revenue = revenue,
        runtime = runtime,
        productionCompanies = productionCompanies.map { it.toDomain() },
        homepage = homepage,
        originCountry = originCountry,
        originalTitle = originalTitle,
        adult = adult
    )
}


