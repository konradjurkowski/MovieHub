package core.utils

import platform.UIKit.UIDevice

actual fun getPlatform(): Platform = Platform.IOS(UIDevice.currentDevice.systemVersion)
