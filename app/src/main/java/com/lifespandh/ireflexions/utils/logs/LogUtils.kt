package com.lifespandh.ireflexions.utils.logs

import android.util.Log

fun logE(message: String, tag: String = "TAG!!!!") {
    Log.e(tag, message)
}

fun logD(message: String, tag: String = "TAG!!!!") {
    Log.d(tag, message)
}

fun logV(message: String, tag: String = "TAG!!!!") {
    Log.v(tag, message)
}