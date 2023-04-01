package com.lifespandh.ireflexions.home.care

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.IS_MINDFULNESS
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import kotlinx.android.synthetic.main.care_center_exercise_fragment.*

class CareCenterExerciseFragment : BaseFragment() {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val args: CareCenterExerciseFragmentArgs by navArgs()
    private val careCenterExerciseAdapter by lazy { CareCenterExerciseAdapter(listOf()) }
    private var isMindFullNess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.care_center_exercise_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getDataFromArguments()
        fetchData()
        setViews()
        setObservers()
    }

    private fun getDataFromArguments() {
        isMindFullNess = args.isMindfulness
        logE("called mind $isMindFullNess")
    }

    private fun fetchData() {
        val requestBody = createJsonRequestBody(IS_MINDFULNESS to isMindFullNess)
        homeViewModel.getCareCenterExercises(requestBody)
    }

    private fun setViews() {
        meditationExerciseView.apply {
            adapter = careCenterExerciseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setObservers() {
        homeViewModel.careCenterExercisesLiveData.observeFreshly(viewLifecycleOwner) {
            careCenterExerciseAdapter.setList(it)
        }
    }

    private fun releaseMediaPlayer() {
        careCenterExerciseAdapter.releaseMediaPlayer()
    }

    companion object {

        fun newInstance() = CareCenterExerciseFragment()
    }

    override fun onPause() {
        careCenterExerciseAdapter.stopPlayer()
        super.onPause()
    }

    override fun onDestroy() {
        careCenterExerciseAdapter.releaseMediaPlayer()
        super.onDestroy()
    }
}