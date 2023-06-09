package com.lifespandh.ireflexions.home.course

import android.os.Bundle
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
import com.lifespandh.ireflexions.models.Course
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.models.UserProgramProgress
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.PROGRAM_ID
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import kotlinx.android.synthetic.main.fragment_course_page.currentCourseProgressBar
import kotlinx.android.synthetic.main.fragment_course_page.currentProgress
import kotlinx.android.synthetic.main.fragment_course_page.rvCourses
import kotlinx.android.synthetic.main.fragment_course_page.currentCourseDescription
import kotlinx.android.synthetic.main.fragment_course_page.currentCourseTitle
import kotlinx.android.synthetic.main.fragment_course_page.tvCurrentProgramCourses

class CourseFragment : BaseFragment(), CoursesAdapter.OnCourseClick {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val courseAdapter by lazy { CoursesAdapter(listOf(), this) }
    private val args: CourseFragmentArgs by navArgs()

    private var parentProgram: Program? = null
    private var userProgramProgress: UserProgramProgress? = null

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
        getBundleValues()
        setViews()
        setObservers()
        getCourses()
    }

    private fun getCourses(){
        val requestBody = createJsonRequestBody(PROGRAM_ID to parentProgram?.id)
        homeViewModel.getCourses(requestBody)
    }

    private fun setObservers(){
        homeViewModel.coursesLiveData.observeFreshly(this) {
            courseAdapter.setList(it)
            setCurrentCourse(it)
        }
    }

    private fun setViews(){
        rvCourses.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        tvCurrentProgramCourses.text = "${parentProgram?.name} courses"

        currentProgress.text = "${userProgramProgress?.courseProgress} %"
        currentCourseProgressBar.progress = userProgramProgress?.courseProgress?.toInt() ?: 0
    }
    private fun getBundleValues() {
        parentProgram = args.parentProgram
        userProgramProgress = args.programProgress
    }

    private fun setCurrentCourse(courses: List<Course>) {
        val courseNumber = userProgramProgress?.courseNumber ?: 0
        if (courseNumber > 0 && courses.size >= courseNumber - 1) {
            val currentCourse = courses.get(courseNumber - 1)
            currentCourseTitle.text = currentCourse.name
            currentCourseDescription.text = currentCourse.description
            // Set image here
        }
    }

    companion object {
        fun newInstance() = CourseFragment()
    }

    override fun onCourseClick(course: Course) {
        val lessonNumber = userProgramProgress?.lessonNumber ?: 0
        val action = CourseFragmentDirections.actionCourseFragmentToLessonFragment(parentProgram= parentProgram ,parentCourse = course, lessonNumber = lessonNumber)
        findNavController().navigate(action)
    }

}