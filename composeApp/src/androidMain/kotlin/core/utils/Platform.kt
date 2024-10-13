package core.utils

import android.os.Build
import dev.gitlive.firebase.storage.Data
import org.konradjurkowski.moviehub.BuildConfig
import java.util.Locale

actual object PlatformInfo {
    actual val platform: Platform = Platform.Android
    actual val systemVersion: String = Build.VERSION.SDK_INT.toString()
    actual fun getLanguageCode(): String = Locale.getDefault().language
    actual fun getCountryCode(): String = Locale.getDefault().country
    actual val isDebug: Boolean = BuildConfig.DEBUG
}

actual fun getFirebaseData(byteArray: ByteArray): Data = Data(byteArray)

