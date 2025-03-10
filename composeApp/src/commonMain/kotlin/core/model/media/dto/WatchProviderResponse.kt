package core.model.media.dto

import core.model.media.WatchProviderInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchProviderResponse(
    val id: Long,
    val results: CountryWatchProviders,
)

@Serializable
data class CountryWatchProviders(
    @SerialName("AT")
    val at: WatchProviderDetails? = null,
    @SerialName("DE")
    val de: WatchProviderDetails? = null,
    @SerialName("ES")
    val es: WatchProviderDetails? = null,
    @SerialName("FR")
    val fr: WatchProviderDetails? = null,
    @SerialName("GB")
    val gb: WatchProviderDetails? = null,
    @SerialName("IT")
    val it: WatchProviderDetails? = null,
    @SerialName("PL")
    val pl: WatchProviderDetails? = null,
    @SerialName("US")
    val us: WatchProviderDetails? = null,
)

@Serializable
data class WatchProviderDetails(
    val link: String,
    val rent: List<WatchProviderInfo>? = null,
    val buy: List<WatchProviderInfo>? = null,
    val flatrate: List<WatchProviderInfo>? = null,
)

fun CountryWatchProviders.getWatchProviderDetails(countryCode: String): WatchProviderDetails? {
    return when (countryCode) {
        "AT" -> at
        "DE" -> de
        "ES" -> es
        "FR" -> fr
        "GB" -> gb
        "IT" -> it
        "PL" -> pl
        "US" -> us
        else -> null
    }
}

fun WatchProviderDetails.mergeList(): List<WatchProviderInfo> {
    val rentList = rent ?: emptyList()
    val buyList = buy ?: emptyList()
    val flatrateList = flatrate ?: emptyList()
    return rentList.plus(buyList).plus(flatrateList)
        .distinctBy { it.providerId }
        .sortedBy { it.displayPriority }
}
