package com.lifespandh.ireflexions.home

import com.lifespandh.ireflexions.api.ApiClient
import com.lifespandh.ireflexions.models.Exercise
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

}