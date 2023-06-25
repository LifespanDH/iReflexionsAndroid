package com.lifespandh.ireflexions.utils.date

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.util.*

const val DATE_TIME_LONG_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val DATE_FORMAT = "yyyy-MM-dd"
const val TIME_FORMAT = "hh:mm:ss"

const val DATE = "date"
const val WEEK_START_DATE = "week_start_date"

const val TIME_DIFFERENCE = 500L // Months

private const val ONLY_DAY_FORMAT = "EEE"
private const val ONLY_MONTH_FORMAT = "MMM"
private const val ONLY_DATE_FORMAT = "dd"

/**
 * Data Structure to be used majorly in
 * WeekAdapter
 * each pair contains: (parsedDate, human readable date, (weekDay, month, date))
 */
typealias DateInfo = Triple<String, String, Triple<String, String, String>>

fun getDateTimeInFormat(timestamp: Long? = null, format: String = DATE_TIME_LONG_FORMAT): String {
    val time = timestamp ?: System.currentTimeMillis()
    val date = Date(time)
    val format = SimpleDateFormat(format)
//    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
    return format.format(date)
}
fun getDateAfterDays(after: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, after)
    return calendar
}
fun getDateInFormat(timestamp: Long? = null, format: String = DATE_FORMAT): String {
    val time = timestamp ?: System.currentTimeMillis()
    val date = Date(time)
    val format = SimpleDateFormat(format)
    return format.format(date)
}

fun Date.getDateInFormat(format: String = DATE_FORMAT): String {
    val format = SimpleDateFormat(format)
    return format.format(this)
}

fun String.getDateInFormat(format: String = DATE_TIME_LONG_FORMAT): Date? {
    val parser = SimpleDateFormat(format, Locale.getDefault())
    return parser.parse(this)
}

fun String.toDate(): Date? {
    val dateFormat = DateFormat.getDateInstance(DateFormat.SHORT)
    val date = dateFormat.parse(this)
    return date
}

fun getCalendarAfterBefore(currentDate: Date, days: Int): Calendar {
    return Calendar.getInstance().apply {
        time = currentDate
        firstDayOfWeek = Calendar.MONDAY
        add(Calendar.DAY_OF_MONTH, days)
        this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
    }
}

fun getTimeInFormat(date: Date? = null, format: String = TIME_FORMAT): String {
    val timeInstance = SimpleDateFormat(format)

    return timeInstance.format(date ?: Date())
}

fun String.getTimeInFormat(format: String = TIME_FORMAT): String {
    val format = SimpleDateFormat(format)
    return getTimeInFormat(format.parse(this))
}

fun getWeekDates(
    calendar: Calendar,
    dayFormat: String = ONLY_DAY_FORMAT,
    monthFormat: String = ONLY_MONTH_FORMAT,
    dateFormat: String = ONLY_DATE_FORMAT
): MutableList<DateInfo> {
    val formatDay = SimpleDateFormat(dayFormat, Locale.US)
    val formatMonth = SimpleDateFormat(monthFormat, Locale.US)
    val formatDate = SimpleDateFormat(dateFormat, Locale.US)
    val dates: MutableList<DateInfo> = mutableListOf()

    for (i in 0..6) {
        when (i) {
            0 -> {
            }
            6 -> {
            }
        }
        dates.add(
            Triple(
                calendar.time.getDateInFormat(),
                getDateInHumanFormat(calendar),
                Triple(
                    formatDay.format(calendar.time),
                    formatMonth.format(calendar.time),
                    formatDate.format(calendar.time)
                )
            )
        )
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dates
}

fun getDateInHumanFormat(calendar: Calendar): String {
    val dayNumberSuffix = getDayNumberSuffix(calendar.get(Calendar.DAY_OF_MONTH))
    val formatter = SimpleDateFormat("EEEE, MMMM dd'$dayNumberSuffix', y", Locale.US)
    return formatter.format(calendar.time)
}

private fun getDayNumberSuffix(day: Int): String? {
    return if (day in 11..13) {
        "th"
    } else when (day % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}

/**
 * From external libraries
 */

fun getStartEndCurrentMonth(difference: Long): Triple<YearMonth, YearMonth, YearMonth> {
    val currentMonth = YearMonth.now()
    val startMonth = currentMonth.minusMonths(difference)
    val endMonth = currentMonth.plusMonths(difference)

    return Triple(startMonth, endMonth, currentMonth)
}

fun getCurrentMonth(): Int {
    return YearMonth.now().monthValue
}

fun getCurrentYear(): Int {
    return YearMonth.now().year
}