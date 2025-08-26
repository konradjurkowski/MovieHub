package com.konradjurkowski.moviehub.core.utils

import android.os.Build
import com.konradjurkowski.moviehub.BuildConfig
import java.util.Locale

actual object PlatformInfo {
    actual val platformType: PlatformType = PlatformType.Android
    actual val systemVersion: String = Build.VERSION.SDK_INT.toString()
    actual val sdkInt: Int = Build.VERSION.SDK_INT
    actual fun getLanguageCode(): String = Locale.getDefault().language
    actual fun getCountryCode(): String = Locale.getDefault().country
    actual val isDebug: Boolean = BuildConfig.DEBUG
}
