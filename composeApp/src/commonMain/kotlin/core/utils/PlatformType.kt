package core.utils

import dev.gitlive.firebase.storage.Data

expect object PlatformInfo {
    val platformType: PlatformType
    val systemVersion: String
    val sdkInt: Int
    fun getLanguageCode(): String
    fun getCountryCode(): String
    val isDebug: Boolean
}

fun PlatformInfo.isAndroid() = platformType == PlatformType.Android
fun PlatformInfo.isIOS() = platformType == PlatformType.IOS

enum class PlatformType {
    Android,
    IOS,
}

expect fun getFirebaseData(byteArray: ByteArray): Data
