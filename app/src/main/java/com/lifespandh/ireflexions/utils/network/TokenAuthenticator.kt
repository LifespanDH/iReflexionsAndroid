package com.lifespandh.ireflexions.utils.network

import com.lifespandh.ireflexions.BuildConfig
import com.lifespandh.ireflexions.api.ApiClient
import com.lifespandh.ireflexions.models.Token
import com.lifespandh.ireflexions.utils.jwt.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenManager.getToken().first()
        }
        return runBlocking {
            val newToken = getNewToken(token)

            if (newToken == null || newToken.token.isNullOrEmpty()) {
                tokenManager.deleteToken()
            }
            newToken?.token?.let { tokenManager.saveToken(it) }
            response.request().newBuilder()
                .header("Authorization", "${newToken?.token}")
                .build()
        }
    }

    private suspend fun getNewToken(refreshToken: String?): Token? {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(ApiClient::class.java)

        val requestBody = createJsonRequestBody(
            REFRESH to refreshToken
        )

        var token: Token? = null

        safeApiCall({
            service.refreshToken(requestBody)
        }, {
            token = it.data
        }, {
            token = null
        })

        return token
    }
}