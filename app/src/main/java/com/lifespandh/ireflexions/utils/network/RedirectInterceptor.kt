package com.lifespandh.ireflexions.utils.network

import com.lifespandh.ireflexions.utils.jwt.TokenManager
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class RedirectInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(chain.request())
        if (response.code == 307) {
            request = response.header("Location")?.let {
                request.newBuilder()
                    .url(it)
                    .build()
            }!!
            response = chain.proceed(request)
        }
        return response
    }

}