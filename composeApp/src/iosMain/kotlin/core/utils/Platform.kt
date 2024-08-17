package core.utils

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIDevice
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform as NativePlatform

actual fun getPlatform(): Platform = Platform.IOS
actual val Platform.systemVersion: String get() = UIDevice.currentDevice.systemVersion
actual val Platform.languageCode: String get() = NSLocale.currentLocale.languageCode
actual val Platform.countryCode: String get() = NSLocale.currentLocale.countryCode ?: ""
@OptIn(ExperimentalNativeApi::class)
actual val Platform.isDebug: Boolean get() = NativePlatform.isDebugBinary
