package com.lifespandh.ireflexions.home.howAmIToday.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentalCondition
import com.lifespandh.ireflexions.models.howAmIToday.HowAmITodayData
import com.lifespandh.ireflexions.models.howAmIToday.TraitCategory
import com.lifespandh.ireflexions.models.howAmIToday.TraitSubCategory
import com.lifespandh.ireflexions.models.howAmIToday.WhatsHappening
import com.lifespandh.ireflexions.utils.network.NetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class HowAmITodayViewModel @Inject constructor(private val howAmITodayRepo: HowAmITodayRepo): ViewModel() {

    private val _traitCategoriesLiveData = MutableLiveData<List<TraitCategory>>()
    val traitCategoryLiveData: LiveData<List<TraitCategory>>
        get() = _traitCategoriesLiveData

    private val _whatsHappeningLiveData = MutableLiveData<List<WhatsHappening>>()
    val whatsHappeningLiveData: LiveData<List<WhatsHappening>>
        get() = _whatsHappeningLiveData

    private val _environmentalConditionsLiveData = MutableLiveData<List<EnvironmentalCondition>>()
    val environmentalConditionsLiveData: LiveData<List<EnvironmentalCondition>>
        get() = _environmentalConditionsLiveData

    private val _howAmITodayLiveData = MutableLiveData<HowAmITodayData>()
    val howAmITodayLiveData: LiveData<HowAmITodayData>
        get() = _howAmITodayLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

////    val selectedTraitsMap: HashMap<Int, Boolean> = hashMapOf()
//    var traitCategoryMap = HashMap<Int, Boolean>()
//    var traitsMap = HashMap<Int, Boolean>()
//    var allTraitsMap = HashMap<Int, Boolean>()
//    var hashMap = HashMap<Int, ArrayList<Int>>()
//    var emotionTraitsMap = HashMap<Int, ArrayList<Int>>()

    val selectedTraitSubCategory: HashMap<Int, MutableList<TraitSubCategory>> = hashMapOf()
    val selectedWhatsHappening: MutableList<WhatsHappening> = mutableListOf()
    val selectedEnvironmentalConditions: MutableList<EnvironmentalCondition> = mutableListOf()
    val newWhatsHappening: MutableLiveData<WhatsHappening?> = MutableLiveData()
    val newEnvironmentalCondition: MutableLiveData<EnvironmentalCondition?> = MutableLiveData()

    fun getTraitCategories() {
        viewModelScope.launch {
            val response = howAmITodayRepo.getTraitCategories()

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _traitCategoriesLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getWhatsHappening() {
        viewModelScope.launch {
            val response = howAmITodayRepo.getWhatsHappening()

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _whatsHappeningLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getEnvironmentalConditions() {
        viewModelScope.launch {
            val response = howAmITodayRepo.getEnvironmentalConditions()

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _environmentalConditionsLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getHowAmITodayData() {
        viewModelScope.launch {
            val response = howAmITodayRepo.getHowAmITodayData()

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _howAmITodayLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }
}