package com.lifespandh.ireflexions.utils.network

import com.lifespandh.ireflexions.utils.jwt.TokenManager
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getToken().first()
        }
        val request = chain.request().newBuilder()
        logE("called token $token")
        request.addHeader("Authorization", "$token")
        return chain.proceed(request.build())
    }
}