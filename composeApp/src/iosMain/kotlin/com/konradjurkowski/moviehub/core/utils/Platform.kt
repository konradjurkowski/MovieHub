package com.konradjurkowski.moviehub.core.utils

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIDevice
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform as NativePlatform

actual object PlatformInfo {
    actual val platformType: PlatformType = PlatformType.IOS
    actual val systemVersion: String = UIDevice.currentDevice.systemVersion
    actual val sdkInt: Int = 0
    actual fun getLanguageCode(): String = NSLocale.currentLocale.languageCode
    actual fun getCountryCode(): String = NSLocale.currentLocale.countryCode ?: ""
    @OptIn(ExperimentalNativeApi::class)
    actual val isDebug: Boolean = NativePlatform.isDebugBinary
}
