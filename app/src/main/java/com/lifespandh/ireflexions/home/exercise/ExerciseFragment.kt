package com.lifespandh.ireflexions.home.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.unity.UnityExerciseFragmentArgs
import com.lifespandh.ireflexions.models.Exercise
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import kotlinx.android.synthetic.main.fragment_exercise.*

class ExerciseFragment : BaseFragment(), ExerciseAdapter.OnExerciseClick {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val exerciseAdapter by lazy { ExerciseAdapter(listOf(), this) }
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
        setRecyclerView()
        getExercises()
        setObservers()
    }

    private fun setRecyclerView() {
        exercisesRecyclerView.apply {
            adapter = exerciseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getExercises() {
        homeViewModel.getExercises()
    }

    private fun setObservers() {
        homeViewModel.exercisesLiveData.observeFreshly(this) {
            exerciseAdapter.setList(it)
        }
    }

    companion object {
        fun newInstance() = ExerciseFragment()
    }

    override fun onExerciseClicked(exercise: Exercise) {
        val action = ExerciseFragmentDirections.actionExerciseFragmentToUnityExerciseFragment(exerciseId = exercise.unityId)
        findNavController().navigate(action)
//        if ((context as MainActivity).tpsManager.isConnected()) {
//            findNavController().navigate(R.id.action_exerciseListFragment_to_unityExerciseFragment,exerciseBundle)
//        } else {
//            (context as MainActivity).showConnectBiofeedbackDialog(requireActivity().getString(
//                R.string.connect_biofeedback
//            ),
//                requireActivity().getString(R.string.connect_biofeedback_desc), cancel = {
//                    findNavController().navigate(R.id.action_exerciseListFragment_to_unityExerciseFragment,exerciseBundle)
//                }, okClickAction = {
//                    startExerciseWithSensor(R.id.action_exerciseListFragment_to_unityExerciseFragment,exerciseBundle)
//                }
//            )
//        }
    }
}