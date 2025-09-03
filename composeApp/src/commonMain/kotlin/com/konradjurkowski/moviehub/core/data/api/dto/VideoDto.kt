package com.konradjurkowski.moviehub.core.data.api.dto

import com.konradjurkowski.moviehub.core.domain.model.media.Video
import com.konradjurkowski.moviehub.core.domain.model.media.VideoType
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Long,
    val type: String,
    val official: Boolean,
    val iso_639_1: String,
    val iso_3166_1: String,
    val publishedAt: String,
)

fun VideoDto.toDomain(): Video {
    return Video(
        id = id,
        name = name,
        key = key,
        site = site,
        size = size,
        type = VideoType.fromValue(type),
        official = official,
        publishedAt = publishedAt,
    )
}
