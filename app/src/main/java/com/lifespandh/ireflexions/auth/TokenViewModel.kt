package com.lifespandh.ireflexions.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifespandh.ireflexions.utils.jwt.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager,
): ViewModel() {

    val token = MutableLiveData<String?>()
    val refreshToken = MutableLiveData<String?>()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getToken().collect {
                withContext(Dispatchers.Main) {
                    token.value = it
                }
            }
            tokenManager.getRefreshToken().collect {
                withContext(Dispatchers.Main) {
                    refreshToken.value = it
                }
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveToken(token)
        }
    }

    fun deleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteToken()
        }
    }

    fun saveRefreshToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveRefreshToken(token)
        }
    }

    fun deleteRefreshToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteRefreshToken()
        }
    }
}