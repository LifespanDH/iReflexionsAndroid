package com.lifespandh.ireflexions.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifespandh.ireflexions.models.Token
import com.lifespandh.ireflexions.models.User
import com.lifespandh.ireflexions.utils.network.MESSAGE
import com.lifespandh.ireflexions.utils.network.NetworkResult
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authRepo: AuthRepo): ViewModel() {

    private val _tokenLiveData = MutableLiveData<Token>()
    val tokenLiveData: LiveData<Token>
        get() = _tokenLiveData

    private val _userRegisteredLiveData = MutableLiveData<String>()
    val userRegisteredLiveData: LiveData<String>
        get() = _userRegisteredLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun loginUser(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = authRepo.loginUser(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _tokenLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun loginCustomUser(user: User) {
        viewModelScope.launch {
            val response = authRepo.loginCustomUser(user)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _tokenLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun refreshToken(requestBody: RequestBody) {
        viewModelScope.launch {
            val response = authRepo.refreshToken(requestBody)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _tokenLiveData.value = data
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

    fun registerUser(user: User) {
        viewModelScope.launch {
            val response = authRepo.registerUser(user)

            when(response) {
                is NetworkResult.Success -> {
                    val data = response.data
                    _userRegisteredLiveData.value = data.get(MESSAGE).asString
                }
                is NetworkResult.Error -> {
                    val error = response.exception
                    _errorLiveData.value = error.toString()
                }
            }
        }
    }

}