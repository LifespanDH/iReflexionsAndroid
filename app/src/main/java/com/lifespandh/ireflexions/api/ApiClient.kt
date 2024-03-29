package com.lifespandh.ireflexions.api

import com.google.gson.JsonObject
import com.lifespandh.ireflexions.models.*
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentalCondition
import com.lifespandh.ireflexions.models.howAmIToday.HowAmITodayData
import com.lifespandh.ireflexions.models.howAmIToday.TraitCategory
import com.lifespandh.ireflexions.models.howAmIToday.WeeklyReport
import com.lifespandh.ireflexions.models.howAmIToday.WhatsHappening
import com.lifespandh.ireflexions.utils.*
import com.lifespandh.ireflexions.utils.network.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET(GET_REGISTERED_PROGRAMS)
    suspend fun getRegisteredProgramList(): List<Program>

    @POST(GET_USER_PROGRAM_PROGRESS)
    suspend fun getUserProgramProgress(): UserProgramProgress

    @POST(ADD_USER_TO_PROGRAM)
    suspend fun addUserToProgram(@Body requestBody: RequestBody): Program

    @POST(GET_COURSES)
    @Headers("Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjgxNjA5NzM4LCJpYXQiOjE2ODE1OTg5MzgsImp0aSI6IjM0NTQ0MWI0ZDZmOTRhYmFiZGM0OTgzYzEzYmIwNTJjIiwidXNlcl9pZCI6Ik5vbmUifQ.vw0ON_CiGDSzeR9TizTDsHjHwqJrJUH9YY12bSM4eG0")
    suspend fun getCourses(@Body requestBody: RequestBody): List<Course>

    @POST(GET_LESSONS)
    suspend fun getLessons(@Body requestBody: RequestBody): List<Lesson>

    @POST(GET_LESSON_QUESTIONS)
    suspend fun getLessonQuestions(@Body requestBody: RequestBody): List<LessonQuestion>

    @POST(SAVE_PROGRAM_PROGRESS)
    suspend fun saveProgramProgress(@Body requestBody: RequestBody): JsonObject

    @GET(GET_SUPPORT_CONTACTS)
    suspend fun getSupportContacts(): List<SupportContact>

    @POST(ADD_SUPPORT_CONTACT)
    suspend fun addSupportContact(@Body supportContact: SupportContact): SupportContact

    @POST(EDIT_SUPPORT_CONTACT)
    suspend fun editSupportContact(@Body supportContact: SupportContact): JsonObject

    @POST(DELETE_SUPPORT_CONTACT)
    suspend fun deleteSupportContact(@Body requestBody: RequestBody): JsonObject

    @POST(GET_JOURNAL_ENTRIES)
    suspend fun getJournalEntries(@Body requestBody: RequestBody): List<DailyCheckInEntry>

    @POST(GET_CARE_CENTER_EXERCISES)
    suspend fun getCareCenterExercises(@Body requestBody: RequestBody): List<CareCenterExercise>

    @GET(GET_RESOURCE_CONTENT)
    suspend fun getResourceContent(): List<ResourceLibraryItem>

    @GET(GET_TRAIT_CATEGORIES)
    suspend fun getTraitCategories(): List<TraitCategory>

    @GET(GET_WHATS_HAPPENING)
    suspend fun getWhatsHappening(): List<WhatsHappening>

    @GET(GET_ENVIRONMENTAL_CONDITIONS)
    suspend fun getEnvironmentalConditions(): List<EnvironmentalCondition>

    @GET(GET_HOW_AM_I_TODAY_DATA)
    suspend fun getHowAmITodayData(): HowAmITodayData

    @POST(ADD_DAILY_CHECK_IN_ENTRY)
    suspend fun addDailyCheckInEntry(@Body dailyCheckInEntry: DailyCheckInEntry): DailyCheckInEntry

    @POST(GET_DAILY_ENTRIES)
    suspend fun getDailyEntries(@Body requestBody: RequestBody): List<DailyCheckInEntry>

    @POST(GET_WEEKLY_ENTRIES)
    suspend fun getWeeklyEntries(@Body requestBody: RequestBody): List<WeeklyReport>

    @POST(GET_MONTHLY_ENTRIES)
    suspend fun getMonthlyReports(@Body requestBody: RequestBody): JsonObject

    // Hey Peers APIs
    @POST(HP_AUTHENTICATE)
    suspend fun heyPeersAuthenticate(@Body requestBody: RequestBody): JsonObject

    @POST(HP_CREATE_USER)
    suspend fun heyPeersCreateUser(@Path("id") id: Int, @Header("Authorization") token: String, @Body requestBody: RequestBody): JsonObject

    @POST(HP_GENERATE_OTL_LINK)
    suspend fun heyPeersGenerateOTLLink(@Path("id") id: Int, @Path("uuid") uuid: String, @Header("Authorization") token: String): JsonObject

    @POST(SAVE_HP_UUID)
    suspend fun saveHPUUID(@Body requestBody: RequestBody): JsonObject
}