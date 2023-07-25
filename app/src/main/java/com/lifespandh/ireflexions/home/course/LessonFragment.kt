package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
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
import com.lifespandh.ireflexions.models.Lesson
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
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

    private val homeViewModel by activityViewModels<HomeViewModel> { viewModelFactory }
    private val lessonAdapter by lazy { LessonAdapter(listOf(), this) }
    private val args: LessonFragmentArgs by navArgs()

//    private var lessonCount = 0
    private var programId: Int = -1
    private var courseId = -1
//    private var courseProgress = 0f
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
        observeResult()
    }

    private fun observeResult() {
        val currentBackStackEntry = findNavController().currentBackStackEntry
        val savedStateHandle = currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Boolean>(LessonContentFragment.LESSON_RESULT)
            ?.observeFreshly(currentBackStackEntry, Observer { result ->
//                if (result) {
//                    lessonCount += 1
//                }
            })
    }

    private fun setObservers() {
        homeViewModel.lessonsLiveData.observeFreshly(this) {
            lessonAdapter.setList(it)
            lessonNumber =
                (homeViewModel.userProgramProgress.value?.courseProgress?.times(it.size))?.toInt() ?: 0
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

    private fun setViews() {
        rvLessons.apply {
            adapter = lessonAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun setCurrentLesson(lessons: List<Lesson>) {
        logE("called $lessonNumber ${homeViewModel.lessonCount.value} ${lessons.size}")
        if (homeViewModel.lessonCount.value!! >= lessons.size) {
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
//            savedStateHandle?.set(RESULT, true)
            homeViewModel.courseCount.value = (homeViewModel.courseCount.value ?: 0)?.plus(1)
            findNavController().navigateUp()
            return
        }
        homeViewModel.lessonCount.value = lessonNumber
        if (lessons.size >= lessonNumber) {
            currentLesson = lessons.get(lessonNumber)
            currentLessonName.text = currentLesson?.name
        }
    }

    private fun getBundleValues() {
        programId = args.programId
        courseId = args.courseId
//        courseProgress = args.courseProgress
//        lessonNumber = args.lessonNumber
    }

    companion object {
        fun newInstance() = LessonFragment()

        const val RESULT = "result"
    }

    override fun onDestroyView() {
        homeViewModel.lessonsLiveData.removeObservers(this)
        super.onDestroyView()
    }

    override fun onLessonClick(lesson: Lesson) {
        val action = LessonFragmentDirections.actionLessonFragmentToLessonContentFragment(programId = programId, courseId = courseId, parentLesson = lesson, currentLesson = currentLesson)
        findNavController().navigate(action)
    }
}