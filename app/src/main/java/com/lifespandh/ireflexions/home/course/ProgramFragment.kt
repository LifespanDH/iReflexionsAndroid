package com.lifespandh.ireflexions.home.course

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.models.UserProgramProgress
import com.lifespandh.ireflexions.utils.dialogs.DialogUtils
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.PROGRAM_ID
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.makeInvisible
import com.lifespandh.ireflexions.utils.ui.makeVisible
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.android.synthetic.main.fragment_course_list.*


class ProgramFragment : BaseFragment(), CourseListProgramAdapter.OnItemClicked {

    private val homeViewModel by activityViewModels<HomeViewModel> { viewModelFactory }
    private val courseListProgramAdapter by lazy { CourseListProgramAdapter(listOf(), this) }
    private val dialogUtils = DialogUtils()
    private var userProgramProgress: UserProgramProgress? = null

    private var currentProgram: Program? = null

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
            val action = ProgramFragmentDirections.actionCourseListFragmentToCourseFragment(parentProgram = currentProgram)
            findNavController().navigate(action)
        }
    }

    private fun getPrograms() {
        homeViewModel.getPrograms()
        homeViewModel.getUserProgramProgress()
    }

    private fun setViews() {
        rvPrograms.apply {
            adapter = courseListProgramAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        browseProgramContainer.isVisible = currentProgram == null
    }


    private fun setObservers() {
        homeViewModel.programsLiveData.observeFreshly(this) {
            courseListProgramAdapter.setList(it)
            updateCurrentProgram(it.filter { it.isRegistered })
        }

        homeViewModel.programProgressLiveData.observeFreshly(this) {
            userProgramProgress = it
            updateProgramProgress()
        }

        homeViewModel.userEnrolledLiveData.observeFreshly(this) {
            if(it != null) {
                dialogUtils.showMessageDialog(requireContext(), "SUCCESS","User enrolled successfully")
                userProgramProgress = null
                courseListProgramAdapter.setEnrolled(it)
                homeViewModel.getUserProgramProgress()
                updateCurrentProgram(listOf(it))
            }
        }
    }

    companion object {
        fun newInstance() = ProgramFragment()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.lessonCount.value = 0
        homeViewModel.courseCount.value = 0
    }

    private fun updateCurrentProgram(currentPrograms: List<Program>) {
        if (currentPrograms.isNullOrEmpty())
            return

        currentProgram = currentPrograms.get(0)
        browseProgramContainer.makeInvisible()
        currentProgramContainer.makeVisible()

        currentProgramTitle.text = currentProgram?.name
    }

    private fun updateProgramProgress() {
        val programProgress = userProgramProgress?.programProgress
        Log.d("progress" , "$programProgress")
        currentProgressTextView.text = if (userProgramProgress?.programProgress == null) "0%" else "${(userProgramProgress?.programProgress?.times(100))?.toInt().toString()}%"
        currentProgramProgressBar.progress = programProgress?.times(100)?.toInt() ?: 0
    }

    override fun onItemClick(program: Program) {
        if (currentProgram == null) {
            //toast("Show registration dialog here")
        } else {
            if (currentProgram?.id == program.id) {
                if (userProgramProgress?.programProgress == 1f) {
                    toast("You have already completed the program")
                    return
                }
                val action = ProgramFragmentDirections.actionCourseListFragmentToCourseFragment(parentProgram = program)
                findNavController().navigate(action)
            } else {
                if (userProgramProgress?.programProgress == 1f) {
                    onProgramEnroll(program)
                } else {
                    toast("You can register to only one program at a time")
                }
            }
        }
    }

    override fun onProgramEnroll(program: Program) {
        val requestBody = createJsonRequestBody(PROGRAM_ID to program.id)
        homeViewModel.addUserToProgram(requestBody)
    }
}