package com.konradjurkowski.moviehub.feature.movies.domain.model

import com.konradjurkowski.moviehub.core.data.api.dto.GenreDto
import com.konradjurkowski.moviehub.core.data.api.dto.ProductionCompanyDto
import com.konradjurkowski.moviehub.core.data.api.dto.ProductionCountryDto
import com.konradjurkowski.moviehub.core.data.api.dto.SpokenLanguageDto
import com.konradjurkowski.moviehub.core.domain.model.media.Cast
import com.konradjurkowski.moviehub.core.domain.model.media.Crew
import com.konradjurkowski.moviehub.core.domain.model.media.Video
import com.konradjurkowski.moviehub.core.domain.model.media.VideoType

data class MovieDetails(
    val id: Long,
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
    val videos: List<Video>,
    val cast: List<Cast>,
    val crew: List<Crew>,
)

val MovieDetails.director get() = crew.firstOrNull { it.job == "Director" }
val MovieDetails.trailers get() = videos
    .filter { it.official && it.site == "YouTube" && it.type == VideoType.TRAILER }
