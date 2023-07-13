package com.lifespandh.ireflexions.utils

import com.google.gson.Gson
import com.google.gson.JsonElement

fun <T> JsonElement.deserializeFromJson(klass: Class<T>): T? {
    val gson = Gson()
    return gson.fromJson(this, klass)
}