package core.utils

sealed class Platform(open val languageCode: String, open val countryCode: String) {
    data class Android(
        val systemVersion: Int,
        override val languageCode: String,
        override val countryCode: String,
    ) : Platform(languageCode, countryCode)
    data class IOS(
        val systemVersion: String,
        override val languageCode: String,
        override val countryCode: String,
    ): Platform(languageCode, countryCode)
}

expect fun getPlatform(): Platform
