package com.lifespandh.ireflexions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.lifespandh.ireflexions.auth.AuthViewModel
import com.lifespandh.ireflexions.auth.LoginActivity
import com.lifespandh.ireflexions.base.BaseActivity
import com.lifespandh.ireflexions.home.HomeActivity
import com.lifespandh.ireflexions.utils.jwt.isJWTExpired
import com.lifespandh.ireflexions.utils.livedata.CombinedLiveData
import com.lifespandh.ireflexions.utils.livedata.combineWith
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : BaseActivity() {

    private val authViewModel by viewModels<AuthViewModel> { viewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setObservers()
    }

    private fun setObservers() {
        CombinedLiveData(tokenViewModel.token, tokenViewModel.refreshToken) { token, refresh ->
            if (token.isNullOrEmpty()) {
                startActivity(LoginActivity.newInstance(this))
            } else {
                val expired = token.isJWTExpired()
                if (expired) {
                    val requestBody = createJsonRequestBody("refresh" to refresh)
                    authViewModel.refreshToken(requestBody)
                } else {
                    sharedPrefs.isLoggedIn = true
                    startActivity(HomeActivity.newInstance(this))
                }
            }
        }.observe(this) {}

        authViewModel.tokenLiveData.observeFreshly(this) {
            logE("called rref $it")
            tokenViewModel.saveToken(it.token)
            tokenViewModel.saveRefreshToken(it.refresh)
            sharedPrefs.isLoggedIn = true
            startActivity(HomeActivity.newInstance(this))
        }

        authViewModel.errorLiveData.observeFreshly(this) {
            startActivity(LoginActivity.newInstance(this))
        }
    }
}