package feature.series.domain.model

import core.model.Genre
import core.model.ProductionCompany
import core.model.ProductionCountry
import core.model.SpokenLanguage
import kotlinx.datetime.Instant

data class SeriesDetails(
    val id: Long,
    val name: String,
    val overview: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val firstAirDate: Instant? = null,
    val lastAirDate: Instant? = null,
    val genres: List<Genre>,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastEpisodeToAir: Episode? = null,
    val nextEpisodeToAir: Episode? = null,
    val numberOfEpisodes: Long,
    val numberOfSeasons: Long,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val popularity: Double,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val seasons: List<Season>,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Double,
    val voteCount: Long,
)

data class Episode(
    val id: Long,
    val name: String,
    val overview: String,
    val stillPath: String? = null,
    val runtime: Long,
    val airDate: Instant? = null,
    val episodeNumber: Long,
    val episodeType: String,
    val productionCode: String,
    val seasonNumber: Long,
    val showId: Long,
    val voteAverage: Double,
    val voteCount: Long,
)

data class Season(
    val id: Long,
    val name: String,
    val overview: String,
    val episodeCount: Long,
    val posterPath: String? = null,
    val airDate: Instant? = null,
    val seasonNumber: Long,
    val voteAverage: Double,
)
