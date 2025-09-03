package com.konradjurkowski.moviehub.core.data.api.dto

import com.konradjurkowski.moviehub.core.domain.model.media.Cast
import kotlinx.serialization.Serializable

@Serializable
data class CastDto(
    val id: Long,
    val name: String,
    val originalName: String,
    val character: String,
    val popularity: Double,
    val imageUrl: String?,
    val adult: Boolean,
    val gender: Long,
    val order: Long,
)

fun CastDto.toDomain(): Cast {
    return Cast(
        id = id,
        name = name,
        character = character,
        popularity = popularity,
        imageUrl = imageUrl,
        adult = adult,
        gender = gender,
        order = order,
    )
}
