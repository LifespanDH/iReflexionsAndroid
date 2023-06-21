package com.lifespandh.ireflexions.utils.date

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

const val DATE_TIME_LONG_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val DATE_FORMAT = "yyyy-MM-dd"
const val TIME_FORMAT = "hh:mm:ss"

const val DATE = "date"

private const val ONLY_DAY_FORMAT = "EEE"
private const val ONLY_MONTH_FORMAT = "MMM"
private const val ONLY_DATE_FORMAT = "dd"

/**
 * Data Structure to be used majorly in
 * WeekAdapter
 * each pair contains: (parsedDate, (weekDay, month, date))
 */
typealias DateInfo = Pair<String, Triple<String, String, String>>

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

fun getWeekDates(calendar: Calendar): MutableList<Pair<String, Triple<String, String, String>>> {
    val formatDay = SimpleDateFormat(ONLY_DAY_FORMAT, Locale.US)
    val formatMonth = SimpleDateFormat(ONLY_MONTH_FORMAT, Locale.US)
    val formatDate = SimpleDateFormat(ONLY_DATE_FORMAT, Locale.US)
    val dates: MutableList<Pair<String, Triple<String, String, String>>> = mutableListOf()

    for (i in 0..6) {
        when (i) {
            0 -> {
            }
            6 -> {
            }
        }
        dates.add(calendar.time.getDateInFormat() to Triple(formatDay.format(calendar.time), formatMonth.format(calendar.time), formatDate.format(calendar.time)))
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dates
}