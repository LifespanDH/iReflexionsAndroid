package com.lifespandh.ireflexions.home.howAmIToday.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentalCondition
import com.lifespandh.ireflexions.models.howAmIToday.HowAmITodayData
import com.lifespandh.ireflexions.models.howAmIToday.PanicAttack
import com.lifespandh.ireflexions.models.howAmIToday.PanicSymptom
import com.lifespandh.ireflexions.models.howAmIToday.PanicTrigger
import com.lifespandh.ireflexions.models.howAmIToday.SleepQuality
import com.lifespandh.ireflexions.models.howAmIToday.TraitCategory
import com.lifespandh.ireflexions.models.howAmIToday.TraitSubCategory
import com.lifespandh.ireflexions.models.howAmIToday.WhatsHappening
import com.lifespandh.ireflexions.utils.network.NetworkResult
import kotlinx.coroutines.launch
import okhttp3.RequestBody
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

    private val _dailyCheckInEntryAddedLiveData = MutableLiveData<DailyCheckInEntry>()
    val dailyCheckInEntryAddedLiveData: LiveData<DailyCheckInEntry>
        get() = _dailyCheckInEntryAddedLiveData

    private val _dailyCheckInEntriesLiveData = MutableLiveData<List<DailyCheckInEntry>>()
    val dailyCheckInEntriesLiveData: LiveData<List<DailyCheckInEntry>>
        get() = _dailyCheckInEntriesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    val selectedTraitSubCategory: HashMap<Int, MutableList<TraitSubCategory>> = hashMapOf()
    val selectedWhatsHappening: MutableList<WhatsHappening> = mutableListOf()
    val selectedEnvironmentalConditions: MutableList<EnvironmentalCondition> = mutableListOf()
    val selectedPanicSymptoms: MutableList<PanicSymptom> = mutableListOf()
    val selectedPanicTriggers: MutableList<PanicTrigger> = mutableListOf()
    val selectedPanicAttack: MutableLiveData<PanicAttack> = MutableLiveData()

    val newWhatsHappening: MutableLiveData<WhatsHappening?> = MutableLiveData()
    val newEnvironmentalCondition: MutableLiveData<EnvironmentalCondition?> = MutableLiveData()
    val newPanicSymptom: MutableLiveData<PanicSymptom> = MutableLiveData()
    val newPanicTrigger: MutableLiveData<PanicTrigger> = MutableLiveData()

    var selectedPosition = -1
        set(value) {
            field = value
        }
        get

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

    fun addDailyCheckInEntry(dailyCheckInEntry: DailyCheckInEntry) {
        viewModelScope.launch {
            val response = howAmITodayRepo.addDailyCheckInEntry(dailyCheckInEntry)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _dailyCheckInEntryAddedLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getDailyEntries(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = howAmITodayRepo.getDailyEntries(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _dailyCheckInEntriesLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun clearData() {
        this.selectedTraitSubCategory.clear()
        this.selectedWhatsHappening.clear()
        this.selectedEnvironmentalConditions.clear()
        this.selectedPanicSymptoms.clear()
        this.selectedPanicTriggers.clear()
        this.selectedPanicAttack.value = null

        this.newWhatsHappening.value = null
        this.newEnvironmentalCondition.value = null
        this.newPanicSymptom.value = null
        this.newPanicTrigger.value = null
    }
}