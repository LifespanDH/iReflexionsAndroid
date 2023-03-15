package com.lifespandh.ireflexions.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lifespandh.ireflexions.di.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}