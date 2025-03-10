package core.model.media

import kotlinx.datetime.Instant

data class Video(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Long,
    val type: VideoType,
    val official: Boolean,
    val publishedAt: Instant?,
)

enum class VideoType(val value: String) {
    BEHIND_THE_SCENES("Behind the Scenes"),
    CLIP("Clip"),
    FEATURETTE("Featurette"),
    TEASER("Teaser"),
    TRAILER("Trailer"),
    UNKNOWN("Unknown");

    companion object {
        fun fromValue(value: String): VideoType {
            return entries.firstOrNull { it.value == value } ?: UNKNOWN
        }
    }
}

fun Video.getThumbnailUrl(): String {
    return "https://img.youtube.com/vi/$key/hqdefault.jpg"
}

fun Video.getVideoUrl(): String {
    return "https://www.youtube.com/watch?v=$key"
}
