package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.PROGRAM_ID
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.runInHandler
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.android.synthetic.main.fragment_course_page.currentCourseContainer
import kotlinx.android.synthetic.main.fragment_course_page.currentCourseProgressBar
import kotlinx.android.synthetic.main.fragment_course_page.currentProgress
import kotlinx.android.synthetic.main.fragment_course_page.rvCourses
import kotlinx.android.synthetic.main.fragment_course_page.currentCourseDescription
import kotlinx.android.synthetic.main.fragment_course_page.currentCourseTitle
import kotlinx.android.synthetic.main.fragment_course_page.tvCurrentProgramCourses

class CourseFragment : BaseFragment(), CoursesAdapter.OnCourseClick {

    private val homeViewModel by activityViewModels<HomeViewModel> { viewModelFactory }
    private val courseAdapter by lazy { CoursesAdapter(listOf(), this) }
    private val args: CourseFragmentArgs by navArgs()

    private var parentProgram: Program? = null
    private var currentCourse: Course? = null
    private var courseNumber: Int? = null
//    private var userProgramProgress: UserProgramProgress? = null

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
        setListeners()
        getCourses()
        observeResult()
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

    private fun setListeners() {
        currentCourseContainer.setOnClickListener {
            currentCourse?.let { it1 -> onCourseClick(it1) }
        }
    }

    private fun setViews() {
        rvCourses.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        tvCurrentProgramCourses.text = "${parentProgram?.name} courses"
        currentProgress.text = "${(homeViewModel.userProgramProgress.value?.courseProgress?.times(100))?.toInt() ?: 0}%"
        runInHandler {
            currentCourseProgressBar.progress = (homeViewModel.userProgramProgress.value?.courseProgress?.times(100))?.toInt() ?: 0
        }
    }

    private fun setViews(currentCourse: Course) {
        currentCourseTitle.text = currentCourse.name
        currentCourseDescription.text = currentCourse.description
    }

    private fun getBundleValues() {
        parentProgram = args.parentProgram
//        userProgramProgress = args.programProgress
    }

    private fun setCurrentCourse(courses: List<Course>) {
        if (homeViewModel.courseCount.value!! >= courses.size) {
            findNavController().navigateUp()
            return
        }
        courseNumber = if (courseNumber == null) ((homeViewModel.userProgramProgress.value?.courseNumber)) else courseNumber
        if (courseNumber!! >= courses.size)
            return
        homeViewModel.courseCount.value = courseNumber
        if (courses.size >= courseNumber!!) {
            currentCourse = courses.get(courseNumber!!)
            currentCourseTitle.text = currentCourse?.name
            currentCourseDescription.text = currentCourse?.description
            // Set image here
        }
    }

    private fun observeResult() {
        val currentBackStackEntry = findNavController().currentBackStackEntry
        val savedStateHandle = currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Boolean>(LessonFragment.RESULT)
            ?.observe(currentBackStackEntry, Observer { result ->
                logE("called back $result")
                if (result)
                    courseNumber = courseNumber?.plus(1)
            })
    }

    companion object {
        fun newInstance() = CourseFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        courseNumber = null
        homeViewModel.coursesLiveData.removeObservers(this)
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.lessonCount.value = 0
    }

    override fun onCourseClick(course: Course) {
        if (course.id != currentCourse?.id) {
            toast("You need to complete the previous courses first")
        } else {
//            val lessonNumber = (userProgramProgress?.lessonNumber?.plus(1)) ?: 1
            val action = CourseFragmentDirections.actionCourseFragmentToLessonFragment(programId = parentProgram?.id ?: -1, courseId = course.id)
            findNavController().navigate(action)
        }
    }

}