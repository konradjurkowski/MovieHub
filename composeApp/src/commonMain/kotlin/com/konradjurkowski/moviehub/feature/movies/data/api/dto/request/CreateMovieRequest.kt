package com.konradjurkowski.moviehub.feature.movies.data.api.dto.request

import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails
import kotlinx.serialization.Serializable

@Serializable
data class CreateMovieRequest(
    val groupId: Long,
    val tmdbId: Long,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterUrl: String? = null,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
)

fun MovieDetails.toCreateRequest(groupId: Long): CreateMovieRequest {
    return CreateMovieRequest(
        groupId = groupId,
        tmdbId = id,
        title = title,
        overview = overview,
        language = language,
        adult = adult,
        posterUrl = posterUrl,
        backgroundUrl = backgroundUrl,
        releaseDate = releaseDate,
    )
}
