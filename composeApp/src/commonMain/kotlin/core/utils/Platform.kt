package core.utils

import dev.gitlive.firebase.storage.Data

expect object PlatformInfo {
    val platform: Platform
    val systemVersion: String
    val sdkInt: Int
    fun getLanguageCode(): String
    fun getCountryCode(): String
    val isDebug: Boolean
}

enum class Platform {
    Android,
    IOS,
}

expect fun getFirebaseData(byteArray: ByteArray): Data
