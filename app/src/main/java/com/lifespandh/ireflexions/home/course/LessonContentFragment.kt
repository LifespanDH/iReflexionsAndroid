package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.models.Course
import com.lifespandh.ireflexions.models.Lesson
import com.lifespandh.ireflexions.models.Program
import kotlinx.android.synthetic.main.fragment_lesson_content.image
import kotlinx.android.synthetic.main.fragment_lesson_content.lessonContentTV
import kotlinx.android.synthetic.main.fragment_lesson_content.takeQuizButton
import kotlinx.android.synthetic.main.fragment_lesson_content.titleTextView


class LessonContentFragment : BaseFragment() {

    private val args: LessonContentFragmentArgs by navArgs()
    private var lesson: Lesson? = null
    private var parentProgram: Program? = null
    private var parentCourse: Course? = null


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
        parentProgram = args.parentProgram
        parentCourse = args.parentCourse
        lesson = args.parentLesson
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

    }

    private fun setListeners(){
        takeQuizButton.setOnClickListener {
            val action = LessonContentFragmentDirections.actionLessonContentFragmentToLessonQuizFragment(parentProgram = parentProgram, parentCourse = parentCourse, parentLesson = lesson)
            findNavController().navigate(action)
        }
    }

    companion object {
        fun newInstance() = LessonContentFragment()
    }
}