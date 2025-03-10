package core.utils

import co.touchlab.kermit.Logger
import kotlinx.datetime.Instant

fun String.toInstant(): Instant? {
    return try {
        // If the string does not contain 'T', it means it only has the date part.
        val dateTimeString = if (this.contains('T')) this else "${this}T00:00:00Z"
        Instant.parse(dateTimeString)
    } catch (e: IllegalArgumentException) {
        Logger.d("DateParser", e, "Failed to parse date from string: $this")
        null
    }
}
