package core.utils

expect fun getPlatform(): Platform
expect val Platform.systemVersion: String
expect val Platform.languageCode: String
expect val Platform.countryCode: String
expect val Platform.isDebug: Boolean

enum class Platform {
    Android,
    IOS,
}
