package core.utils

import android.os.Build

actual fun getPlatform(): Platform = Platform.Android(Build.VERSION.SDK_INT)
