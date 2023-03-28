package com.lifespandh.ireflexions.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseActivity

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, HomeActivity::class.java)
    }
}