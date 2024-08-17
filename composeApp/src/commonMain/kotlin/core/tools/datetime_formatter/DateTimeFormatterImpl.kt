package core.tools.datetime_formatter

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

class DateTimeFormatterImpl : DateTimeFormatter {
    override fun formatToYear(date: Instant?): String {
        val formatter = LocalDateTime.Format { year() }
        val localDateTime = date?.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime?.let { formatter.format(it) } ?: ""
    }

    override fun formatToDayMonthYear(date: Instant?): String {
        val formatter = LocalDateTime.Format {
            dayOfMonth()
            char('.')
            monthNumber()
            char('.')
            year()
        }
        val localDateTime = date?.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime?.let { formatter.format(it) } ?: ""
    }
}
