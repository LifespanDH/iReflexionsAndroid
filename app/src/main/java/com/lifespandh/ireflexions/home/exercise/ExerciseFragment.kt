package com.lifespandh.ireflexions.home.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.utils.livedata.observeFreshly

class ExerciseFragment : BaseFragment() {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getExercises()
        setObservers()
    }

    private fun getExercises() {
        homeViewModel.getExercises()
    }

    private fun setObservers() {
        homeViewModel.exercisesLiveData.observeFreshly(this) {

        }
    }

    companion object {
        fun newInstance() = ExerciseFragment()
    }
}