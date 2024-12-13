package feature.series.data.api.dto

import core.model.Genre
import core.model.ProductionCountry
import core.model.SpokenLanguage
import core.model.dto.ProductionCompanyDto
import core.model.dto.toDomain
import core.utils.Constants
import core.utils.toInstant
import feature.series.domain.model.Episode
import feature.series.domain.model.Season
import feature.series.domain.model.SeriesDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailsDto(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    @SerialName("in_production")
    val inProduction: Boolean,
    val languages: List<String>,
    @SerialName("last_air_date")
    val lastAirDate: String? = null,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: EpisodeDto? = null,
    val name: String,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: EpisodeDto? = null,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Long,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Long,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,
    val seasons: List<SeasonDto>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Long,
)

fun SeriesDetailsDto.toDomain(): SeriesDetails {
    return SeriesDetails(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath?.let { Constants.IMAGE_BASE_URL + it },
        backdropPath = backdropPath?.let { Constants.IMAGE_BASE_URL + it },
        firstAirDate = firstAirDate?.toInstant(),
        lastAirDate = lastAirDate?.toInstant(),
        genres = genres,
        inProduction = inProduction,
        languages = languages,
        lastEpisodeToAir = lastEpisodeToAir?.toDomain(),
        nextEpisodeToAir = nextEpisodeToAir?.toDomain(),
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalName = originalName,
        popularity = popularity,
        productionCompanies = productionCompanies.map { it.toDomain() },
        productionCountries = productionCountries,
        seasons = seasons.filter { it.airDate != null }.map { it.toDomain() },
        spokenLanguages = spokenLanguages,
        status = status,
        tagline = tagline,
        type = type,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}

@Serializable
data class EpisodeDto(
    val id: Long,
    val name: String,
    val overview: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Long,
    @SerialName("air_date")
    val airDate: String? = null,
    @SerialName("episode_number")
    val episodeNumber: Long,
    @SerialName("episode_type")
    val episodeType: String,
    @SerialName("production_code")
    val productionCode: String,
    val runtime: Long? = null,
    @SerialName("season_number")
    val seasonNumber: Long,
    @SerialName("show_id")
    val showId: Long,
    @SerialName("still_path")
    val stillPath: String? = null,
)

fun EpisodeDto.toDomain(): Episode {
    return Episode(
        id = id,
        name = name,
        overview = overview,
        stillPath = stillPath?.let { Constants.IMAGE_BASE_URL + it },
        runtime = runtime,
        airDate = airDate?.toInstant(),
        episodeNumber = episodeNumber,
        episodeType = episodeType,
        productionCode = productionCode,
        seasonNumber = seasonNumber,
        showId = showId,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}

@Serializable
data class SeasonDto(
    @SerialName("air_date")
    val airDate: String? = null,
    @SerialName("episode_count")
    val episodeCount: Long,
    val id: Long,
    val name: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("season_number")
    val seasonNumber: Long,
    @SerialName("vote_average")
    val voteAverage: Double,
)

fun SeasonDto.toDomain(): Season {
    return Season(
        id = id,
        name = name,
        overview = overview,
        episodeCount = episodeCount,
        posterPath = posterPath?.let { Constants.IMAGE_BASE_URL + it },
        airDate = airDate?.toInstant(),
        seasonNumber = seasonNumber,
        voteAverage = voteAverage,
    )
}
