package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.exercise.ExerciseAdapter
import com.lifespandh.ireflexions.home.exercise.ExerciseFragment
import com.lifespandh.ireflexions.home.exercise.ExerciseFragmentDirections
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.android.synthetic.main.fragment_course_list.*
import kotlinx.android.synthetic.main.program_item.txt_program
import kotlinx.android.synthetic.main.program_item.view.img_program
import kotlinx.android.synthetic.main.program_item.view.programProgressBar
import kotlinx.android.synthetic.main.program_item.view.txt_enroll
import kotlinx.android.synthetic.main.program_item.view.txt_program


class CourseListFragment : BaseFragment(), CourseListProgramAdapter.OnItemClicked {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val courseListProgramAdapter by lazy { CourseListProgramAdapter(listOf(), this) }
    private var currentProgram: List<Program>? = null
    private var userProgramProgress: JsonObject? = null

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
        getPrograms()
        setViews()
        setObservers()
        setListeners()
    }

    private fun setListeners(){
        currentProgramContainer.setOnClickListener {
            val courseprogress = userProgramProgress?.get("course_progress")
            val action = CourseListFragmentDirections.actionCourseListFragmentToCourseFragment(parentProgram = currentProgram?.get(0), courseProgress = 0.0F)
            findNavController().navigate(action)
        }
    }

    private fun getPrograms() {
        homeViewModel.getPrograms()
        homeViewModel.getRegisteredProgramList()
        homeViewModel.getUserProgramProgress()
    }

    private fun setViews(){
        rvPrograms.apply {
            adapter = courseListProgramAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        if(currentProgram == null)
        {
            browseProgramsTV.visibility = View.VISIBLE
        }
    }


    private fun setObservers() {
        homeViewModel.programsLiveData.observeFreshly(this) {
            courseListProgramAdapter.setList(it)
        }

        homeViewModel.registeredProgramsLiveData.observeFreshly(this){
            currentProgram = it
            if(it?.isEmpty() != true) {
                updateCurrentProgram()
            }
        }

        homeViewModel.programProgressLiveData.observeFreshly(this){
            userProgramProgress = it
            updateProgramProgress()
        }


        tokenViewModel.token.observeFreshly(this) {
            logE("called token $it")
        }

        tokenViewModel.refreshToken.observeFreshly(this) {
            logE("called refresh $it")
        }
    }

    companion object {
        fun newInstance() = CourseListFragment()
    }
    private fun updateCurrentProgram() {
        browseProgramsTV.visibility = View.INVISIBLE
        currentProgramContainer.visibility = View.VISIBLE
        currentProgramItem.txt_enroll.visibility = View.INVISIBLE
        currentProgramItem.txt_program.text = currentProgram?.get(0)!!.name
        Glide.with(this).load(currentProgram?.get(0)!!.img).into(currentProgramItem.img_program)
    }

    private fun updateProgramProgress() {
        val programProgress = userProgramProgress?.get("program_progress")
        currentProgramItem.programProgressBar.progress = 4.5.toInt()
    }

    override fun onItemClick(program: Program) {

        if (program.id == currentProgram?.get(0)?.id) {

            val courseprogress = userProgramProgress?.get("course_progress")
            val action = CourseListFragmentDirections.actionCourseListFragmentToCourseFragment(parentProgram = program, courseProgress = 0.0F)
            findNavController().navigate(action)
        }
        else
        {
            //toast
        }
    }

    override fun onProgramEnroll(program: Program) {

        homeViewModel.addUserToProgram(program.id)
    }
}