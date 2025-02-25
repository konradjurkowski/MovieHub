package core.model

import core.utils.constants.MovieApiConstants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
    val id: Long,
    @SerialName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerialName("origin_country")
    val originCountry: String,
)

fun ProductionCompany.getImageUrl(): String? {
    return logoPath?.let { MovieApiConstants.IMAGE_BASE_URL + it }
}
