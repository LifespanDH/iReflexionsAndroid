package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.care.EditSupportContactFragmentArgs
import com.lifespandh.ireflexions.models.Course
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.ID
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import kotlinx.android.synthetic.main.fragment_course_page.*
import kotlinx.android.synthetic.main.fragment_home.*

class CourseFragment : BaseFragment(), CoursesAdapter.OnCourseClick {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val courseAdapter by lazy { CoursesAdapter(listOf(), this) }
    private var parentProgram: Program? = null
    private val args: CourseFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_course_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setViews()
        getBundleValues()
        setObservers()
        getCourses()
    }

    private fun getCourses(){
        val requestBody = createJsonRequestBody(ID to parentProgram?.id)
        homeViewModel.getCourses(requestBody)
    }

    private fun setObservers(){
        homeViewModel.coursesLiveData.observeFreshly(this){
            courseAdapter.setList(it)
        }
    }

    private fun setViews(){
        rvCourses.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun getBundleValues() {
        parentProgram = args.parentProgram
    }

    companion object {
        fun newInstance() = CourseFragment()
    }

    override fun onCourseClick(course: Course) {
        val action = CourseFragmentDirections.actionCourseFragmentToLessonFragment(parentCourse = course)
        findNavController().navigate(action)
    }

}