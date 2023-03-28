package com.lifespandh.ireflexions.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lifespandh.ireflexions.auth.TokenViewModel
import com.lifespandh.ireflexions.di.ViewModelFactory
import com.lifespandh.ireflexions.utils.sharedPrefs.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val tokenViewModel by viewModels<TokenViewModel> { viewModelFactory }
    protected val sharedPrefs = SharedPrefs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}