package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.Course
import com.lifespandh.ireflexions.models.Lesson
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.COURSE_ID
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import kotlinx.android.synthetic.main.fragment_lesson.rvLessons

class LessonFragment : BaseFragment(), LessonAdapter.OnLessonClick {

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val lessonAdapter by lazy { LessonAdapter(listOf(), this) }
    private var parentCourse: Course? = null
    private val args: LessonFragmentArgs by navArgs()

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
        setObservers()
        setViews()
    }

    private fun setObservers() {
        homeViewModel.lessonsLiveData.observeFreshly(this) {
            lessonAdapter.setList(it)
        }
    }
    private fun getLessons(){
        val requestBody = createJsonRequestBody(COURSE_ID to parentCourse?.id)
        homeViewModel.getLessons(requestBody)
    }

    private fun setViews(){
        rvLessons.apply {
            adapter = lessonAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getBundleValues() {
        parentCourse = args.parentCourse
    }

    companion object {
        fun newInstance() = LessonFragment()
    }

    override fun onLessonClick(lesson: Lesson) {

    }
}