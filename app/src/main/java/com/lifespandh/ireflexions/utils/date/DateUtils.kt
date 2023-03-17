package com.lifespandh.ireflexions.utils.date

import java.text.DateFormat
import java.util.*

fun getDateTimeInFormat(timestamp: Long? = null): String {
    val time = timestamp ?: System.currentTimeMillis()
    val date = Date(time)
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
    return dateFormat.format(date)
}