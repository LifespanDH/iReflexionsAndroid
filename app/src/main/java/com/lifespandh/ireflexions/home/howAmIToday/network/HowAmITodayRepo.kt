package com.lifespandh.ireflexions.home.howAmIToday.network

import com.lifespandh.ireflexions.api.ApiClient
import com.lifespandh.ireflexions.models.howAmIToday.TraitCategory
import com.lifespandh.ireflexions.utils.network.NetworkResult
import com.lifespandh.ireflexions.utils.network.safeApiCall
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
}