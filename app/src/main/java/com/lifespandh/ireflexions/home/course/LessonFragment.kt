package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.Course
import com.lifespandh.ireflexions.models.Lesson
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.COURSE_ID
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import kotlinx.android.synthetic.main.activity_registration.view.name
import kotlinx.android.synthetic.main.fragment_course_page.currentCourseDescription
import kotlinx.android.synthetic.main.fragment_course_page.currentCourseTitle
import kotlinx.android.synthetic.main.fragment_home.courses
import kotlinx.android.synthetic.main.fragment_lesson.currentLessonContainer
import kotlinx.android.synthetic.main.fragment_lesson.currentLessonName
import kotlinx.android.synthetic.main.fragment_lesson.rvLessons

class LessonFragment : BaseFragment(), LessonAdapter.OnLessonClick {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val lessonAdapter by lazy { LessonAdapter(listOf(), this) }
    private val args: LessonFragmentArgs by navArgs()

    private var programId: Int = -1
    private var courseId = -1
    private var currentLesson: Lesson? = null
    private var lessonNumber: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lesson, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getBundleValues()
        getLessons()
        setListeners()
        setObservers()
        setViews()
    }

    private fun setObservers() {
        homeViewModel.lessonsLiveData.observeFreshly(this) {
            lessonAdapter.setList(it)
            setCurrentLesson(it)
        }
    }
    private fun getLessons(){
        val requestBody = createJsonRequestBody(COURSE_ID to courseId)
        homeViewModel.getLessons(requestBody)
    }

    private fun setListeners() {
        currentLessonContainer.setOnClickListener {
            currentLesson?.let { it1 -> onLessonClick(it1) }
        }
    }

    private fun setViews(){
        rvLessons.apply {
            adapter = lessonAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun setCurrentLesson(lessons: List<Lesson>) {
        if (lessonNumber > 0 && lessons.size >= lessonNumber - 1) {
            currentLesson = lessons.get(lessonNumber - 1)
            currentLessonName.text = currentLesson?.name
        }
    }

    private fun getBundleValues() {
        programId = args.programId
        courseId = args.courseId
        lessonNumber = args.lessonNumber
    }

    companion object {
        fun newInstance() = LessonFragment()
    }

    override fun onLessonClick(lesson: Lesson) {
        val action = LessonFragmentDirections.actionLessonFragmentToLessonContentFragment(programId = programId, courseId = courseId, parentLesson = lesson, currentLesson = currentLesson)
        findNavController().navigate(action)
    }
}