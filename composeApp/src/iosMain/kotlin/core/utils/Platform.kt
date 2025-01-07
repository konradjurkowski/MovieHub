package core.utils

import dev.gitlive.firebase.storage.Data
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import platform.Foundation.NSData
import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.create
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIDevice
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform as NativePlatform

actual object PlatformInfo {
    actual val platform: Platform = Platform.IOS
    actual val systemVersion: String = UIDevice.currentDevice.systemVersion
    actual val sdkInt: Int = 0
    actual fun getLanguageCode(): String = NSLocale.currentLocale.languageCode
    actual fun getCountryCode(): String = NSLocale.currentLocale.countryCode ?: ""
    @OptIn(ExperimentalNativeApi::class)
    actual val isDebug: Boolean = NativePlatform.isDebugBinary
}

actual fun getFirebaseData(byteArray: ByteArray): Data = Data(byteArray.toNSData())

@BetaInteropApi
@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData = memScoped {
    NSData.create(bytes = allocArrayOf(this@toNSData), length = this@toNSData.size.toULong())
}
