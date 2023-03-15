package com.lifespandh.ireflexions.auth

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authRepo: AuthRepo): ViewModel() {
}