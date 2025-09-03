package com.konradjurkowski.moviehub.feature.movies.data.api.dto

import com.konradjurkowski.moviehub.feature.movies.domain.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Long,
    val groupId: Long? = null,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterUrl: String? = null,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
)

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        groupId = groupId,
        title = title,
        overview = overview,
        language = language,
        adult = adult,
        posterUrl = posterUrl,
        backgroundUrl = backgroundUrl,
        releaseDate = releaseDate,
    )
}
