package core.utils

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIDevice

actual fun getPlatform(): Platform {
    return Platform.IOS(
        systemVersion = UIDevice.currentDevice.systemVersion,
        languageCode = NSLocale.currentLocale.languageCode,
        countryCode = NSLocale.currentLocale.countryCode ?: "",
    )
}
