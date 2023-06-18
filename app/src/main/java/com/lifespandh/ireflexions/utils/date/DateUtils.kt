package com.lifespandh.ireflexions.utils.date

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun getDateTimeInFormat(timestamp: Long? = null): String {
    val time = timestamp ?: System.currentTimeMillis()
    val date = Date(time)
    val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
//    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
    return format.format(date)
}
fun getDateAfterDays(after: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, after)
    return calendar
}
fun getDateInFormat(timestamp: Long? = null): String {
    val time = timestamp ?: System.currentTimeMillis()
    val date = Date(time)
    val format = SimpleDateFormat("YYYY-MM-DD")
    return format.format(date)
}

fun String.toDate(): Date? {
    val dateFormat = DateFormat.getDateInstance(DateFormat.SHORT)
    val date = dateFormat.parse(this)
    return date
}

fun getTimeInFormat(date: Date? = null): String {
    val timeInstance = SimpleDateFormat("hh:mm:ss")

    return timeInstance.format(date ?: Date())
}

fun String.getTimeInFormat(): String {
    val format = SimpleDateFormat("hh:mm:ss")
    return getTimeInFormat(format.parse(this))
}