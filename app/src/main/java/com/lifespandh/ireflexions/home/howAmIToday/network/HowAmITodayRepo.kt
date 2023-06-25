package com.lifespandh.ireflexions.home.howAmIToday.network

import com.google.gson.JsonObject
import com.lifespandh.ireflexions.api.ApiClient
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentalCondition
import com.lifespandh.ireflexions.models.howAmIToday.HowAmITodayData
import com.lifespandh.ireflexions.models.howAmIToday.TraitCategory
import com.lifespandh.ireflexions.models.howAmIToday.WeeklyReport
import com.lifespandh.ireflexions.models.howAmIToday.WhatsHappening
import com.lifespandh.ireflexions.utils.network.NetworkResult
import com.lifespandh.ireflexions.utils.network.safeApiCall
import okhttp3.RequestBody
import javax.inject.Inject

class HowAmITodayRepo @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getTraitCategories(): NetworkResult<List<TraitCategory>> {
        var networkResult: NetworkResult<List<TraitCategory>>? = null

        safeApiCall({
            apiClient.getTraitCategories()
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getWhatsHappening(): NetworkResult<List<WhatsHappening>> {
        var networkResult: NetworkResult<List<WhatsHappening>>? = null

        safeApiCall({
            apiClient.getWhatsHappening()
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getEnvironmentalConditions(): NetworkResult<List<EnvironmentalCondition>> {
        var networkResult: NetworkResult<List<EnvironmentalCondition>>? = null

        safeApiCall({
            apiClient.getEnvironmentalConditions()
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getHowAmITodayData(): NetworkResult<HowAmITodayData> {
        var networkResult: NetworkResult<HowAmITodayData>? = null

        safeApiCall({
            apiClient.getHowAmITodayData()
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun addDailyCheckInEntry(dailyCheckInEntry: DailyCheckInEntry): NetworkResult<DailyCheckInEntry> {
        var networkResult: NetworkResult<DailyCheckInEntry>? = null

        safeApiCall({
            apiClient.addDailyCheckInEntry(dailyCheckInEntry)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getDailyEntries(requestBody: RequestBody): NetworkResult<List<DailyCheckInEntry>> {
        var networkResult: NetworkResult<List<DailyCheckInEntry>>? = null

        safeApiCall({
            apiClient.getDailyEntries(requestBody)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getWeeklyEntries(requestBody: RequestBody): NetworkResult<List<WeeklyReport>> {
        var networkResult: NetworkResult<List<WeeklyReport>>? = null

        safeApiCall({
            apiClient.getWeeklyEntries(requestBody)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getMonthlyReports(requestBody: RequestBody): NetworkResult<JsonObject> {
        var networkResult: NetworkResult<JsonObject>? = null

        safeApiCall({
            apiClient.getMonthlyReports(requestBody)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }
}