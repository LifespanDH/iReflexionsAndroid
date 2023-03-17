package com.lifespandh.ireflexions.auth

import android.net.NetworkRequest
import com.lifespandh.ireflexions.api.ApiClient
import com.lifespandh.ireflexions.models.Token
import com.lifespandh.ireflexions.utils.network.NetworkResult
import com.lifespandh.ireflexions.utils.network.safeApiCall
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRepo @Inject constructor(private val apiClient: ApiClient) {

    suspend fun loginUser(requestBody: RequestBody): NetworkResult<Token> {
        var networkResult: NetworkResult<Token>? = null

        safeApiCall({
            apiClient.loginUser(requestBody)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }
}