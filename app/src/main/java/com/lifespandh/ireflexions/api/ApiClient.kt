package com.lifespandh.ireflexions.api

import com.google.gson.JsonObject
import com.lifespandh.ireflexions.models.SurveyQuestion
import com.lifespandh.ireflexions.models.SurveyResponse
import com.lifespandh.ireflexions.models.Token
import com.lifespandh.ireflexions.models.User
import com.lifespandh.ireflexions.utils.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

@JvmSuppressWildcards
interface ApiClient {

    @POST(REFRESH_TOKEN)
    suspend fun refreshToken(@Body requestBody: RequestBody): Token

    @POST(LOGIN_USER)
    suspend fun loginUser(@Body requestBody: RequestBody): Token

    @POST(LOGIN_CUSTOM_USER)
    suspend fun loginCustomUser(@Body user: User): Token

    @POST(REGISTER_USER)
    suspend fun registerUser(@Body user: User): JsonObject

    @GET(GET_SURVEY_QUESTIONS)
    suspend fun getSurveyQuestions(): List<SurveyQuestion>

    @POST(SAVE_SURVEY_QUESTIONS)
    suspend fun saveSurveyQuestions(@Body responses: List<SurveyResponse>): JsonObject

}