package core.model.media

import core.utils.constants.MovieApiConstants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchProviderInfo(
    @SerialName("provider_id")
    val providerId: Long,
    @SerialName("provider_name")
    val providerName: String,
    @SerialName("logo_path")
    val logoPath: String?,
    @SerialName("display_priority")
    val displayPriority: Long,
)

fun WatchProviderInfo.getImageUrl(): String? {
    return logoPath?.let { MovieApiConstants.IMAGE_BASE_URL + it }
}
