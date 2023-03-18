package com.lifespandh.ireflexions.utils.date

import java.text.DateFormat
import java.time.LocalDate
import java.util.*

fun getDateTimeInFormat(timestamp: Long? = null): String {
    val time = timestamp ?: System.currentTimeMillis()
    val date = Date(time)
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
    return dateFormat.format(date)
}
fun getDateAfterDays(after: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, after)
    return calendar
}
fun getDateInFormat(timestamp: Long? = null): String {
    val time = timestamp ?: System.currentTimeMillis()
    val date = Date(time)
    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
    return dateFormat.format(date)
}

fun String.toDate(): Date? {
    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
    val date = dateFormat.parse(this)
    return date
}