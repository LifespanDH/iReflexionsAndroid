package com.lifespandh.ireflexions.utils.network

import okhttp3.RequestBody
import org.json.JSONObject
import okhttp3.MediaType

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
        MediaType.parse("application/json; charset=utf-8"),
        JSONObject(mapOf(*params)).toString())