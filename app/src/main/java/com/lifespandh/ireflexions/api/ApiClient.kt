package com.lifespandh.ireflexions.api

import com.lifespandh.ireflexions.models.Token
import com.lifespandh.ireflexions.utils.LOGIN_USER
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

@JvmSuppressWildcards
interface ApiClient {

    @POST(LOGIN_USER)
    suspend fun loginUser(@Body requestBody: RequestBody): Token

}