package com.lifespandh.ireflexions.home.course

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.Course
import com.lifespandh.ireflexions.models.Lesson
import com.lifespandh.ireflexions.models.LessonQuestion
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.models.QUESTION_TYPE
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.COURSE_ID
import com.lifespandh.ireflexions.utils.network.COURSE_NUMBER
import com.lifespandh.ireflexions.utils.network.LESSON_ID
import com.lifespandh.ireflexions.utils.network.LESSON_NUMBER
import com.lifespandh.ireflexions.utils.network.PROGRAM_ID
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import kotlinx.android.synthetic.main.item_multiplechoice.view.answersRecyclerView
import kotlinx.android.synthetic.main.item_quiz.view.customAnswerEditText
import kotlinx.android.synthetic.main.item_quiz.view.multipleChoiceContainer
import kotlinx.android.synthetic.main.item_quiz.view.question_text
import kotlinx.android.synthetic.main.item_quiz.view.trueFalseContainer
import kotlinx.android.synthetic.main.lesson_quiz.nextButton
import kotlinx.android.synthetic.main.lesson_quiz.previousButton
import kotlinx.android.synthetic.main.lesson_quiz.questionsRecyclerView


class LessonQuizFragment : BaseFragment(), QuestionsAdapter.OnAnswerSelected {

    private var lesson: Lesson? = null
    private var programId: Int = -1
    private var courseId = -1

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val args: LessonQuizFragmentArgs by navArgs()
    private lateinit var viewPager: ViewPager2
    private var lessons = listOf<LessonQuestion>()
    private var questionNumber = 1
    private val selectedAnswers = mutableMapOf<Int, String>()

    private val questionsAdapter by lazy { QuestionsAdapter(listOf(), this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lesson_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getBundleValues()
        getLessonQuestions()
        setListeners()
        setObservers()
        setViews()
    }

    private fun setViews() {
        questionsRecyclerView.apply {
            adapter = questionsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getBundleValues() {
        programId = args.programId
        courseId = args.courseId
        lesson = args.parentLesson
    }

    private fun setListeners() {
        previousButton.setOnClickListener {
            selectedAnswers[questionNumber] = ""
            questionNumber -= 1
        }

        nextButton.setOnClickListener {
            selectedAnswers[questionNumber] = ""
            questionNumber += 1
        }
    }

    private fun setObservers() {
        homeViewModel.lessonQuestionsLiveData.observeFreshly(this) {
            lessons = it
            questionsAdapter.setList(it)
        }

    }

    private fun getLessonQuestions() {
        val requestBody = createJsonRequestBody(LESSON_ID to lesson?.id)
        homeViewModel.getLessonQuestions(requestBody)
    }

    private fun saveProgress() {
        val requestBody = createJsonRequestBody(COURSE_ID to courseId, LESSON_ID to lesson?.id, PROGRAM_ID to programId)
        homeViewModel.saveProgramProgress(requestBody)
    }

    override fun onAnswerSelected(choice: String, answerPosition: Int, questionId: Int) {
        logE("called $choice $answerPosition $questionId")
    }

}