package com.lifespandh.ireflexions.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseActivity
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : BaseActivity() {

    private val authViewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        init()
    }

    private fun init() {
        setListeners()
        setObservers()
    }

    private fun setListeners() {

    }

    private fun setObservers() {
        authViewModel.userRegisteredLiveData.observeFreshly(this) {
            toast(it)
        }

        authViewModel.errorLiveData.observeFreshly(this) {
            toast(it)
        }
    }
}