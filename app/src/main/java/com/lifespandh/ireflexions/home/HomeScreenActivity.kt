package com.lifespandh.ireflexions.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.auth.RegistrationActivity
import com.lifespandh.ireflexions.base.BaseActivity

class HomeScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, HomeScreenActivity::class.java)
    }
}