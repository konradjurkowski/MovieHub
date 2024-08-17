package core.model.dto

import core.model.ProductionCompany
import core.utils.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyDto (
    val id: Long,
    @SerialName("logo_path")
    val logoPath: String? = null,
    val name: String,
    @SerialName("origin_country")
    val originCountry: String
)

fun ProductionCompanyDto.toDomain(): ProductionCompany {
    return ProductionCompany(
        id = id,
        logoPath = logoPath?.let { Constants.IMAGE_BASE_URL + it },
        name = name,
        originCountry = originCountry
    )
}
