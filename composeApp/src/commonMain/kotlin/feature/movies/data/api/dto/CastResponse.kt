package feature.movies.data.api.dto

import core.utils.Constants
import feature.movies.domain.model.Cast
import feature.movies.domain.model.CastData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse (
    val id: Long,
    val cast: List<CastDto>,
    val crew: List<CastDto>,
)

@Serializable
data class CastDto (
    val id: Long,
    val name: String,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val job: String? = null,
    val character: String? = null,
    val gender: Long,
    val adult: Boolean,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("cast_id")
    val castId: Long? = null,
    @SerialName("credit_id")
    val creditId: String,
    val order: Long? = null,
    val department: String? = null,
)

fun CastResponse.toDomain(): CastData {
    return CastData(
        id = id,
        cast = cast.map { it.toDomain() },
        crew = crew.map { it.toDomain() },
    )
}

fun CastDto.toDomain(): Cast {
    return Cast(
        id = id,
        name = name,
        character = character,
        job = job,
        profilePath = profilePath?.let { Constants.IMAGE_BASE_URL + it },
    )
}
