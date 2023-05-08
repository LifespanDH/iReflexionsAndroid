package com.lifespandh.ireflexions.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.lifespandh.ireflexions.models.*
import com.lifespandh.ireflexions.models.howAmI.DailyCheckInEntry
import com.lifespandh.ireflexions.utils.logs.logD
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

    private val _supportContactEditedLiveData = MutableLiveData<Boolean>()
    val supportContactEditedLiveData: LiveData<Boolean>
        get() = _supportContactEditedLiveData

    private val _supportContactDeletedLiveData = MutableLiveData<Boolean>()
    val supportContactDeletedLiveData: LiveData<Boolean>
        get() = _supportContactDeletedLiveData

    private val _dailyCheckInEntriesLiveData = MutableLiveData<List<DailyCheckInEntry>>()
    val dailyCheckInEntryLiveData: LiveData<List<DailyCheckInEntry>>
        get() = _dailyCheckInEntriesLiveData

    private val _careCenterExercisesLiveData = MutableLiveData<List<CareCenterExercise>>()
    val careCenterExercisesLiveData: LiveData<List<CareCenterExercise>>
        get() = _careCenterExercisesLiveData

    private val _programsLiveData = MutableLiveData<List<Program>>()
    val programsLiveData: LiveData<List<Program>>
        get() = _programsLiveData

    private val _registeredProgramsLiveData = MutableLiveData<List<Program>>()
    val registeredProgramsLiveData: LiveData<List<Program>>
        get() = _registeredProgramsLiveData

    private val _programProgressLiveData = MutableLiveData<UserProgramProgress>()
    val programProgressLiveData: LiveData<UserProgramProgress>
        get() = _programProgressLiveData


    private val _coursesLiveData = MutableLiveData<List<Course>>()
    val coursesLiveData: LiveData<List<Course>>
        get() = _coursesLiveData

    private val _lessonsLiveData = MutableLiveData<List<Lesson>>()
    val lessonsLiveData: LiveData<List<Lesson>>
        get() = _lessonsLiveData

    private val _lessonQuestionsLiveData = MutableLiveData<List<LessonQuestion>>()
    val lessonQuestionsLiveData: LiveData<List<LessonQuestion>>
        get() = _lessonQuestionsLiveData

    private val _saveProgressLiveData = MutableLiveData<JsonObject>()
    val saveProgressLiveData: LiveData<JsonObject>
        get() = _saveProgressLiveData

    private val _userEnrolledLiveData = MutableLiveData<Boolean>()
    val userEnrolledLiveData: LiveData<Boolean>
        get() = _userEnrolledLiveData

    private val _resourceContentLiveData = MutableLiveData<List<ResourceLibraryItem>>()
    val resourceContentLiveData: LiveData<List<ResourceLibraryItem>>
        get() = _resourceContentLiveData


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

    fun editSupportContact(supportContact: SupportContact) {
        viewModelScope.launch {
            val response = homeRepo.editSupportContact(supportContact)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _supportContactEditedLiveData.value = true
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun deleteSupportContact(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = homeRepo.deleteSupportContact(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _supportContactDeletedLiveData.value = true
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

    fun getPrograms() {
        viewModelScope.launch {
            val response = homeRepo.getPrograms()

            when(response) {
                is NetworkResult.Success -> {
                    Log.d("ProgramsApiCall", ""+response)
                    val data = response.data
                    _programsLiveData.value = data
                }
                is NetworkResult.Error -> {
                    Log.d("ProgramsApiCall", ""+response.exception)
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getRegisteredProgramList(){
        viewModelScope.launch {
            val response = homeRepo.getRegisteredProgramList()

            when(response) {
                is NetworkResult.Success -> {
                    Log.d("RegisteredProgramsApiCall", ""+response)
                    val data = response.data
                    _registeredProgramsLiveData.value = data
                }
                is NetworkResult.Error -> {
                    Log.d("RegisteredProgramsApiCall", ""+response.exception)
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getUserProgramProgress(){
        viewModelScope.launch {
            val response = homeRepo.getUserProgramProgress()

            when(response) {
                is NetworkResult.Success -> {
                    Log.d("ProgramProgressApiCall", ""+response)
                    val data = response.data
                    _programProgressLiveData.value = data
                }
                is NetworkResult.Error -> {
                    Log.d("ProgramProgressApiCall", ""+response.exception)
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }
    fun addUserToProgram(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = homeRepo.addUserToProgram(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    logD("$response")
                    val data = response.data
                    _userEnrolledLiveData.value = true
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }


    fun getCourses(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = homeRepo.getCourses(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    Log.d("CoursesApiCall", ""+response)
                    val data = response.data
                    _coursesLiveData.value = data
                }
                is NetworkResult.Error -> {
                    Log.d("CoursesApiCall", ""+response)
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getLessons(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = homeRepo.getLessons(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    Log.d("LessonsApiCall", ""+response)
                    val data = response.data
                    _lessonsLiveData.value = data
                }
                is NetworkResult.Error -> {
                    Log.d("LessonsApiCall", ""+response)
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun getLessonQuestions(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = homeRepo.getLessonQuestions(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    Log.d("LessonQuestionsApiCall", ""+response)
                    val data = response.data
                    _lessonQuestionsLiveData.value = data
                }
                is NetworkResult.Error -> {
                    Log.d("LessonQuestionsApiCall", ""+response)
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun saveProgramProgress(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = homeRepo.saveProgramProgress(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    Log.d("SaveProgressApiCall", ""+response)
                    val data = response.data
                    _saveProgressLiveData.value = data
                }
                is NetworkResult.Error -> {
                    Log.d("SaveProgressApiCall", ""+response)
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }
    fun getResourceContent() {
        viewModelScope.launch {
            val response = homeRepo.getResourceContent()

            when(response) {
                is NetworkResult.Success -> {
                    Log.d("ResourcesApiCall", ""+response)
                    val data = response.data
                    _resourceContentLiveData.value = data
                }
                is NetworkResult.Error -> {
                    Log.d("ResourcesApiCall", ""+response)
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