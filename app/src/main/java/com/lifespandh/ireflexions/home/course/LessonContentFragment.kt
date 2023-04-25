package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.models.Lesson
import kotlinx.android.synthetic.main.fragment_lesson_content.lessonContentTV
import kotlinx.android.synthetic.main.fragment_lesson_content.takeQuizButton
import kotlinx.android.synthetic.main.fragment_lesson_content.titleTextView


class LessonContentFragment : BaseFragment() {

  //private val args: LessonContentFragmentArgs by navArgs()
    private val lesson: Lesson? = null


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

    }

    private fun setViews() {
        titleTextView.text
        //setImage
        lessonContentTV.text
    }

    private fun setObservers() {

    }

    private fun setListeners(){
        takeQuizButton.setOnClickListener {

        }
    }

    companion object {
        fun newInstance() = LessonContentFragment()
    }
}