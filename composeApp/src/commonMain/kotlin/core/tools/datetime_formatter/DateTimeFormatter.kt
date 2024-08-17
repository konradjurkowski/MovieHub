package core.tools.datetime_formatter

import kotlinx.datetime.Instant

interface DateTimeFormatter {
    fun formatToYear(date: Instant?): String
    fun formatToDayMonthYear(date: Instant?): String
}
