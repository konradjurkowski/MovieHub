package com.konradjurkowski.moviehub.feature.movies.data.api.dto

import com.konradjurkowski.moviehub.core.data.api.dto.CastDto
import com.konradjurkowski.moviehub.core.data.api.dto.CrewDto
import com.konradjurkowski.moviehub.core.data.api.dto.GenreDto
import com.konradjurkowski.moviehub.core.data.api.dto.ProductionCompanyDto
import com.konradjurkowski.moviehub.core.data.api.dto.ProductionCountryDto
import com.konradjurkowski.moviehub.core.data.api.dto.SpokenLanguageDto
import com.konradjurkowski.moviehub.core.data.api.dto.VideoDto
import com.konradjurkowski.moviehub.core.data.api.dto.toDomain
import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: Long? = null,
    val groupId: Long? = null,
    val tmdbId: Long,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val genres: List<GenreDto>,
    val homepage: String?,
    val originCountry: List<String>,
    val popularity: Double,
    val posterUrl: String?,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
    val productionCompanies: List<ProductionCompanyDto>,
    val productionCountries: List<ProductionCountryDto>,
    val revenue: Long,
    val runtime: Long,
    val spokenLanguages: List<SpokenLanguageDto>,
    val status: String,
    val tagline: String,
    val videos: List<VideoDto>,
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
)

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        groupId = groupId,
        tmdbId = tmdbId,
        title = title,
        overview = overview,
        language = language,
        adult = adult,
        genres = genres,
        homepage = homepage,
        originCountry = originCountry,
        popularity = popularity,
        posterUrl = posterUrl,
        backgroundUrl = backgroundUrl,
        releaseDate = releaseDate,
        productionCompanies = productionCompanies,
        productionCountries = productionCountries,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguages,
        status = status,
        tagline = tagline,
        videos = videos.map { it.toDomain() },
        cast = cast.map { it.toDomain() },
        crew = crew.map { it.toDomain() },
    )
}
