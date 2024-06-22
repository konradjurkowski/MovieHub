package core.utils

import android.os.Build
import java.util.Locale

actual fun getPlatform(): Platform {
    return Platform.Android(
        systemVersion = Build.VERSION.SDK_INT,
        languageCode = Locale.getDefault().language,
        countryCode = Locale.getDefault().country,
    )
}
