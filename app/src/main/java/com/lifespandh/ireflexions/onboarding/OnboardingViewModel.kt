package com.lifespandh.ireflexions.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifespandh.ireflexions.models.SurveyQuestion
import com.lifespandh.ireflexions.models.SurveyResponse
import com.lifespandh.ireflexions.utils.network.NetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(private val onboardingRepo: OnboardingRepo): ViewModel() {

    private val _surveyQuestionsLiveData = MutableLiveData<List<SurveyQuestion>>()
    val surveyQuestionsLiveData: LiveData<List<SurveyQuestion>>
        get() = _surveyQuestionsLiveData

    private val _surveyResponseLiveData = MutableLiveData<Boolean>()
    val surveyResponseLiveData: LiveData<Boolean>
        get() = _surveyResponseLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun getSurveyQuestions() {
        viewModelScope.launch {
            val response = onboardingRepo.getSurveyQuestions()

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _surveyQuestionsLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun saveSurveyQuestions(responses: MutableList<SurveyResponse>) {
        viewModelScope.launch {
            val response = onboardingRepo.saveSurveyQuestions(responses)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _surveyResponseLiveData.value = true
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }
}