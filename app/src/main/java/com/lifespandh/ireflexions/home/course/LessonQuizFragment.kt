package com.lifespandh.ireflexions.home.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.models.Lesson
import com.lifespandh.ireflexions.models.LessonQuestion
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.COURSE_ID
import com.lifespandh.ireflexions.utils.network.LESSON_ID
import com.lifespandh.ireflexions.utils.network.PROGRAM_ID
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.android.synthetic.main.lesson_quiz.questionsRecyclerView
import kotlinx.android.synthetic.main.lesson_quiz.submitQuizButton

class LessonQuizFragment : BaseFragment(), QuestionsAdapter.OnAnswerSelected {

    private var lesson: Lesson? = null
    private var programId: Int = -1
    private var courseId = -1

    private val homeViewModel by activityViewModels<HomeViewModel> { viewModelFactory }
    private val args: LessonQuizFragmentArgs by navArgs()
    private lateinit var viewPager: ViewPager2
    private var lessonQuestions = listOf<LessonQuestion>()

    private val selectedAnswers = mutableMapOf<Int, Int>()

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
        submitQuizButton.setOnClickListener {
            var correctAnswers = 0
            lessonQuestions.forEach {
                val correctAnswer = it.correctAnswer
                val userAnswer = selectedAnswers.get(it.id) ?: -1

                if (userAnswer != -1 && correctAnswer == userAnswer + 1) {
                    correctAnswers += 1
                }
            }
            // Need to add api call here
            toast("You got $correctAnswers out of ${lessonQuestions.size} correct")
            saveProgress()
        }
    }

    private fun setObservers() {
        homeViewModel.lessonQuestionsLiveData.observeFreshly(this) {
            lessonQuestions = it
            questionsAdapter.setList(it)
        }

        homeViewModel.saveProgressLiveData.observeFreshly(this) {
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(QUIZ_RESULT, true)
            findNavController().navigateUp()
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
        selectedAnswers[questionId] = answerPosition
    }

    companion object {

        const val QUIZ_RESULT = "quiz_result"
    }

}