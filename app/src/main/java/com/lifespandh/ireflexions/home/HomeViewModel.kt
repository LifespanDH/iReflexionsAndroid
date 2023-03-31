package com.lifespandh.ireflexions.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifespandh.ireflexions.models.CareCenterExercise
import com.lifespandh.ireflexions.models.howAmI.DailyCheckInEntry
import com.lifespandh.ireflexions.models.Exercise
import com.lifespandh.ireflexions.models.SupportContact
import com.lifespandh.ireflexions.utils.network.NetworkResult
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepo: HomeRepo): ViewModel() {

    private val _exercisesLiveData = MutableLiveData<List<Exercise>>()
    val exercisesLiveData: LiveData<List<Exercise>>
        get() = _exercisesLiveData

    private val _supportContactsLiveData = MutableLiveData<List<SupportContact>>()
    val supportContactsLiveData: LiveData<List<SupportContact>>
        get() = _supportContactsLiveData

    private val _supportContactAddedLiveData = MutableLiveData<Boolean>()
    val supportContactAddedLiveData: LiveData<Boolean>
        get() = _supportContactAddedLiveData

    private val _dailyCheckInEntriesLiveData = MutableLiveData<List<DailyCheckInEntry>>()
    val dailyCheckInEntryLiveData: LiveData<List<DailyCheckInEntry>>
        get() = _dailyCheckInEntriesLiveData

    private val _careCenterExercisesLiveData = MutableLiveData<List<CareCenterExercise>>()
    val careCenterExercisesLiveData: LiveData<List<CareCenterExercise>>
        get() = _careCenterExercisesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    var selectedPosition = -1
        set(value) {
            field = value
        }
        get

    fun getExercises() {
        viewModelScope.launch {
            val response = homeRepo.getExercises()

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _exercisesLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getSupportContacts() {
        viewModelScope.launch {
            val response = homeRepo.getSupportContacts()

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _supportContactsLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun addSupportContact(supportContact: SupportContact) {
        viewModelScope.launch {
            val response = homeRepo.addSupportContact(supportContact)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _supportContactAddedLiveData.value = true
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getJournalEntries(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = homeRepo.getJournalEntries(requestBody)

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

    fun getCareCenterExercises(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = homeRepo.getCareCenterExercises(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _careCenterExercisesLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

//    fun changeSelectedPosition(position: Int) {
//        this.selectedPosition = position
//    }

}