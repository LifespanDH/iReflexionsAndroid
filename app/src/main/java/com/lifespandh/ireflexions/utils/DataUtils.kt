package com.lifespandh.ireflexions.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

fun <T> JsonElement.deserializeFromJson(klass: Class<T>): T? {
    val gson = Gson()
    val token = TypeToken.get(klass)
    return gson.fromJson(this, token.type)
}