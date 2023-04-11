package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.exercise.ExerciseAdapter
import com.lifespandh.ireflexions.home.exercise.ExerciseFragment
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import kotlinx.android.synthetic.main.fragment_course_list.*


class CourseListFragment : BaseFragment() {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val courseListProgramAdapter by lazy { CourseListProgramAdapter(listOf()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setViews()
        setListeners()
        setObservers()
        getPrograms()
    }

    private fun getPrograms() {
        homeViewModel.getPrograms()
    }

    private fun setViews(){
        rvPrograms.apply {
            adapter = courseListProgramAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setListeners(){

    }

    private fun setObservers() {
        homeViewModel.programsLiveData.observeFreshly(this){
            courseListProgramAdapter.setList(it)
        }
    }

    companion object {
        fun newInstance() = CourseListFragment()
    }

}