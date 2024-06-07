package feature.movies.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse (
    val genres: List<Genre>
)

@Serializable
data class Genre (
    val id: Long,
    val name: String
)
