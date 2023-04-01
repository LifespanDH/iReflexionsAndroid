package com.lifespandh.ireflexions.home

import com.google.gson.JsonObject
import com.lifespandh.ireflexions.api.ApiClient
import com.lifespandh.ireflexions.models.CareCenterExercise
import com.lifespandh.ireflexions.models.howAmI.DailyCheckInEntry
import com.lifespandh.ireflexions.models.Exercise
import com.lifespandh.ireflexions.models.SupportContact
import com.lifespandh.ireflexions.utils.network.NetworkResult
import com.lifespandh.ireflexions.utils.network.safeApiCall
import okhttp3.RequestBody
import javax.inject.Inject

class HomeRepo @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getExercises(): NetworkResult<List<Exercise>> {
        var networkResult: NetworkResult<List<Exercise>>? = null

        safeApiCall({
            apiClient.getExercises()
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getSupportContacts(): NetworkResult<List<SupportContact>> {
        var networkResult: NetworkResult<List<SupportContact>>? = null

        safeApiCall({
            apiClient.getSupportContacts()
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun addSupportContact(supportContact: SupportContact): NetworkResult<JsonObject> {
        var networkResult: NetworkResult<JsonObject>? = null

        safeApiCall({
            apiClient.addSupportContact(supportContact)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun editSupportContact(supportContact: SupportContact): NetworkResult<JsonObject> {
        var networkResult: NetworkResult<JsonObject>? = null

        safeApiCall({
            apiClient.editSupportContact(supportContact)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun deleteSupportContact(requestBody: RequestBody): NetworkResult<JsonObject> {
        var networkResult: NetworkResult<JsonObject>? = null

        safeApiCall({
            apiClient.deleteSupportContact(requestBody)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getJournalEntries(requestBody: RequestBody): NetworkResult<List<DailyCheckInEntry>> {
        var networkResult: NetworkResult<List<DailyCheckInEntry>>? = null

        safeApiCall({
            apiClient.getJournalEntries(requestBody)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getCareCenterExercises(requestBody: RequestBody): NetworkResult<List<CareCenterExercise>> {
        var networkResult: NetworkResult<List<CareCenterExercise>>? = null

        safeApiCall({
            apiClient.getCareCenterExercises(requestBody)
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

}