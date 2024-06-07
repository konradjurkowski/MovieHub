package core.utils

sealed class Platform {
    data class Android(val systemVersion: Int) : Platform()
    data class IOS(val systemVersion: String): Platform()
}

expect fun getPlatform(): Platform
