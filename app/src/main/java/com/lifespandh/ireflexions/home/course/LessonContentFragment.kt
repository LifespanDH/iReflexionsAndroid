package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.Course
import com.lifespandh.ireflexions.models.Lesson
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.android.synthetic.main.fragment_lesson_content.image
import kotlinx.android.synthetic.main.fragment_lesson_content.lessonContentTV
import kotlinx.android.synthetic.main.fragment_lesson_content.takeQuizButton
import kotlinx.android.synthetic.main.fragment_lesson_content.titleTextView


class LessonContentFragment : BaseFragment() {

    private val args: LessonContentFragmentArgs by navArgs()

    private var currentLesson: Lesson? = null
    private var lesson: Lesson? = null
    private var programId: Int = -1
    private var courseId = -1

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getBundleValues()
        setListeners()
        setObservers()
        setViews()
    }

    private fun getBundleValues() {
        programId = args.programId
        courseId = args.courseId
        lesson = args.parentLesson
        currentLesson = args.currentLesson
    }

    private fun setViews() {
        titleTextView.text = lesson?.name
        Glide.with(this)
            .load(lesson?.image)
            .into(image)
        //setImage
        lessonContentTV.text = lesson?.content
        lessonContentTV.setMovementMethod(ScrollingMovementMethod())
    }

    private fun setObservers() {
        val currentBackStackEntry = findNavController().currentBackStackEntry
        val savedStateHandle = currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<String>(LessonQuizFragment.QUIZ_RESULT)
            ?.observe(currentBackStackEntry, Observer { result ->
                findNavController().navigateUp()
            })
    }

    private fun setListeners(){
        takeQuizButton.setOnClickListener {
            if (currentLesson?.id != lesson?.id) {
                toast("You cannot skip previous lessons")
            } else {
                val action = LessonContentFragmentDirections.actionLessonContentFragmentToLessonQuizFragment(programId = programId, courseId = courseId, parentLesson = lesson)
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        fun newInstance() = LessonContentFragment()
    }
}