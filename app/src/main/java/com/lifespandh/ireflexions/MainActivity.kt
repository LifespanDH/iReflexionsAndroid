package com.lifespandh.ireflexions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lifespandh.ireflexions.auth.LoginActivity
import com.lifespandh.ireflexions.base.BaseActivity
import com.lifespandh.ireflexions.utils.jwt.isJWTExpired

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val token = sharedPrefs.accessToken
        if (token.isNullOrEmpty()) {
            startActivity(LoginActivity.newInstance(this))
        } else {
            // Token is present and not expired
        }
    }
}