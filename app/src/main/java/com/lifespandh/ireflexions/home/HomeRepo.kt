package com.lifespandh.ireflexions.home

import com.google.gson.JsonObject
import com.lifespandh.ireflexions.api.ApiClient
import com.lifespandh.ireflexions.models.Exercise
import com.lifespandh.ireflexions.models.SupportContact
import com.lifespandh.ireflexions.utils.network.NetworkResult
import com.lifespandh.ireflexions.utils.network.safeApiCall
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

}