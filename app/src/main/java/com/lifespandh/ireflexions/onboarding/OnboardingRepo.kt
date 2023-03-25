package com.lifespandh.ireflexions.onboarding

import com.google.gson.JsonObject
import com.lifespandh.ireflexions.api.ApiClient
import com.lifespandh.ireflexions.models.SurveyQuestion
import com.lifespandh.ireflexions.utils.network.NetworkResult
import com.lifespandh.ireflexions.utils.network.safeApiCall
import javax.inject.Inject

class OnboardingRepo @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getSurveyQuestions(): NetworkResult<List<SurveyQuestion>> {
        var networkResult: NetworkResult<List<SurveyQuestion>>? = null

        safeApiCall({
            apiClient.getSurveyQuestions()
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }
}