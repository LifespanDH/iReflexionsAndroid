package com.lifespandh.ireflexions.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lifespandh.ireflexions.di.ViewModelFactory
import com.lifespandh.ireflexions.utils.sharedPrefs.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val sharedPrefs = SharedPrefs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}