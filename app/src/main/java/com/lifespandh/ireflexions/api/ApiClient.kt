package com.lifespandh.ireflexions.api

import com.google.gson.JsonObject
import com.lifespandh.ireflexions.models.*
import com.lifespandh.ireflexions.models.howAmI.DailyCheckInEntry
import com.lifespandh.ireflexions.utils.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

@JvmSuppressWildcards
interface ApiClient {

    @POST(REFRESH_TOKEN)
    suspend fun refreshToken(@Body requestBody: RequestBody): Token

    @POST(LOGIN_USER)
    suspend fun loginUser(@Body requestBody: RequestBody): Token

    @POST(LOGIN_CUSTOM_USER)
    suspend fun loginCustomUser(@Body requestBody: RequestBody): Token

    @POST(REGISTER_USER)
    suspend fun registerUser(@Body user: User): JsonObject

    @GET(GET_SURVEY_QUESTIONS)
    suspend fun getSurveyQuestions(): List<SurveyQuestion>

    @POST(SAVE_SURVEY_QUESTIONS)
    suspend fun saveSurveyQuestions(@Body responses: List<SurveyResponse>): JsonObject

    @GET(GET_EXERCISES)
    suspend fun getExercises(): List<Exercise>

    @POST(GET_PROGRAMS)
    suspend fun getPrograms(): List<Program>

    @POST(GET_COURSES)
    @Headers("Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjgxNjA5NzM4LCJpYXQiOjE2ODE1OTg5MzgsImp0aSI6IjM0NTQ0MWI0ZDZmOTRhYmFiZGM0OTgzYzEzYmIwNTJjIiwidXNlcl9pZCI6Ik5vbmUifQ.vw0ON_CiGDSzeR9TizTDsHjHwqJrJUH9YY12bSM4eG0")
    suspend fun getCourses(@Body requestBody: RequestBody): List<Course>

    @POST(GET_LESSONS)
    suspend fun getLessons(@Body requestBody: RequestBody): List<Lesson>

    @GET(GET_SUPPORT_CONTACTS)
    suspend fun getSupportContacts(): List<SupportContact>

    @POST(ADD_SUPPORT_CONTACT)
    suspend fun addSupportContact(@Body supportContact: SupportContact): JsonObject

    @POST(EDIT_SUPPORT_CONTACT)
    suspend fun editSupportContact(@Body supportContact: SupportContact): JsonObject

    @POST(DELETE_SUPPORT_CONTACT)
    suspend fun deleteSupportContact(@Body requestBody: RequestBody): JsonObject

    @POST(GET_JOURNAL_ENTRIES)
    suspend fun getJournalEntries(@Body requestBody: RequestBody): List<DailyCheckInEntry>

    @POST(GET_CARE_CENTER_EXERCISES)
    suspend fun getCareCenterExercises(@Body requestBody: RequestBody): List<CareCenterExercise>
}