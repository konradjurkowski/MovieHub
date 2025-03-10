package core.model.media

import kotlinx.serialization.Serializable

@Serializable
data class Genre (
    val id: Long,
    val name: String,
)
