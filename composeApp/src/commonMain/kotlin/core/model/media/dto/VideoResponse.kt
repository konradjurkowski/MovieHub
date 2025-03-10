package core.model.media.dto

import core.model.media.Video
import core.model.media.VideoType
import core.utils.toInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    val id: Long,
    val results: List<VideoDto>,
)

@Serializable
data class VideoDto(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Long,
    val type: String,
    val official: Boolean,
    @SerialName("iso_639_1")
    val iso639_1: String,
    @SerialName("iso_3166_1")
    val iso3166_1: String,
    @SerialName("published_at")
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
        publishedAt = publishedAt.toInstant(),
    )
}
