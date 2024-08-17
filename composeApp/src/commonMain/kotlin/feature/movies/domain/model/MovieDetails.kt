package feature.movies.domain.model

import core.model.Genre
import core.model.ProductionCompany
import kotlinx.datetime.Instant

data class MovieDetails(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val budget: Long,
    val genres: List<Genre>,
    val tagline: String,
    val status: String,
    val popularity: Double,
    val releaseDate: Instant? = null,
    val revenue: Long,
    val runtime: Long,
    val productionCompanies: List<ProductionCompany>,
    val homepage: String,
    val originCountry: List<String>,
    val originalTitle: String,
    val adult: Boolean,
)
