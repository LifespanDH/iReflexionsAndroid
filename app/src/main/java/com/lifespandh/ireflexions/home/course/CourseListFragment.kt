package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.models.UserProgramProgress
import com.lifespandh.ireflexions.utils.dialogs.DialogUtils
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.COURSE_PROGRESS
import com.lifespandh.ireflexions.utils.network.PROGRAM_ID
import com.lifespandh.ireflexions.utils.network.PROGRAM_PROGRESS
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import kotlinx.android.synthetic.main.fragment_course_list.*
import kotlinx.android.synthetic.main.program_item.view.img_program
import kotlinx.android.synthetic.main.program_item.view.programProgressBar
import kotlinx.android.synthetic.main.program_item.view.txt_enroll
import kotlinx.android.synthetic.main.program_item.view.txt_program


class CourseListFragment : BaseFragment(), CourseListProgramAdapter.OnItemClicked {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val courseListProgramAdapter by lazy { CourseListProgramAdapter(listOf(), this) }
    private var currentPrograms: List<Program>? = null
    private val dialogUtils = DialogUtils()
    private lateinit var userProgramProgress: UserProgramProgress

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
            val courseprogress = userProgramProgress?.courseProgress
            val action = CourseListFragmentDirections.actionCourseListFragmentToCourseFragment(parentProgram = currentPrograms?.get(0), programProgress = userProgramProgress)
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

        if(currentPrograms == null)
        {
            browseProgramsTV.visibility = View.VISIBLE
        }
    }


    private fun setObservers() {
        homeViewModel.programsLiveData.observeFreshly(this) {
            courseListProgramAdapter.setList(it)
        }

        homeViewModel.registeredProgramsLiveData.observeFreshly(this){
            currentPrograms = it
            if(it?.isEmpty() != true) {
                updateCurrentProgram()
            }
        }

        homeViewModel.programProgressLiveData.observeFreshly(this){
            userProgramProgress = it
            updateProgramProgress()
        }

        homeViewModel.userEnrolledLiveData.observeFreshly(this) {
            if(it) {
                dialogUtils.showMessageDialog(requireContext(), "SUCCESS","User registered successfully")
            }
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
        val currentProgram = currentPrograms?.get(0)
        browseProgramsTV.visibility = View.INVISIBLE
        currentProgramContainer.visibility = View.VISIBLE
        currentProgramItem.txt_enroll.visibility = View.INVISIBLE
        currentProgramItem.txt_program.text = currentProgram?.name


        Glide.with(this)
            .load(currentProgram?.image)
            .centerCrop()
            .placeholder(R.drawable.program_copingwithcovidicon)
            .error(R.drawable.program_copingwithcovidicon)
            .into(currentProgramItem.img_program)

        courseListProgramAdapter.setCurrentProgram(currentPrograms?.first() ?: null)
    }

    private fun updateProgramProgress() {
        val programProgress = userProgramProgress?.programProgress
        currentProgramItem.programProgressBar.progress = programProgress?.toInt() ?: 0
    }

    override fun onItemClick(program: Program) {

        if (program.id == currentPrograms?.get(0)?.id) {

            val courseprogress = userProgramProgress?.courseProgress
            val action = CourseListFragmentDirections.actionCourseListFragmentToCourseFragment(parentProgram = program, programProgress = userProgramProgress)
            findNavController().navigate(action)
        }
        else
        {
            //toast
        }
    }

    override fun onProgramEnroll(program: Program) {
        val requestBody = createJsonRequestBody(PROGRAM_ID to program.id)
        homeViewModel.addUserToProgram(requestBody)
    }
}