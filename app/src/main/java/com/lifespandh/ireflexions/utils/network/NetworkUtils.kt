package com.lifespandh.ireflexions.utils.network

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

suspend fun <T> safeApiCall(
    call: suspend() -> T,
    onSuccess: (NetworkResult.Success<T>) -> Unit,
    onFailure: (NetworkResult.Error) -> Unit
) {
    runCatching {
        val response = call()
        onSuccess.invoke(NetworkResult.Success(response))
    }.onFailure {
        it.printStackTrace()
        onFailure.invoke(NetworkResult.Error(it.message))
    }
}

fun createJsonRequestBody(vararg params: Pair<Any, *>): RequestBody =
    RequestBody.create(
        "application/json; charset=utf-8".toMediaTypeOrNull(),
        JSONObject(mapOf(*params)).toString())