package com.lifespandh.ireflexions.base

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.lifespandh.ireflexions.di.ViewModelFactory
import com.lifespandh.ireflexions.utils.sharedPrefs.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseDialogFragment: DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val sharedPrefs = SharedPrefs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

}