package com.lifespandh.ireflexions.home.course

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
import com.lifespandh.ireflexions.utils.network.COURSE_ID
import com.lifespandh.ireflexions.utils.network.LESSON_ID
import com.lifespandh.ireflexions.utils.network.PROGRAM_ID
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import kotlinx.android.synthetic.main.item_multiplechoice.view.answersRecyclerView
import kotlinx.android.synthetic.main.item_quiz.view.customAnswerEditText
import kotlinx.android.synthetic.main.item_quiz.view.multipleChoiceContainer
import kotlinx.android.synthetic.main.item_quiz.view.question_text
import kotlinx.android.synthetic.main.item_quiz.view.trueFalseContainer
import kotlinx.android.synthetic.main.lesson_quiz.itemQuiz
import kotlinx.android.synthetic.main.lesson_quiz.nextButton
import kotlinx.android.synthetic.main.lesson_quiz.previousButton


class LessonQuizFragment : BaseFragment() {

    private var lesson: Lesson? = null
    private var parentProgram: Program? = null
    private var parentCourse: Course? = null
    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val args: LessonQuizFragmentArgs by navArgs()
    private lateinit var viewPager: ViewPager2
    private var lessons = listOf<LessonQuestion>()
    private var questionNumber = 1
    private val selectedAnswers = mutableMapOf<Int, String>()

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
        getLessonQuestions()
        getBundleValues()
        setListeners()
        setObservers()
        setViews()
    }

    private fun setQuestion(questionNumber: Int) {
        val lessonQuestion = lessons[questionNumber - 1]
        itemQuiz.multipleChoiceContainer.makeGone()
        itemQuiz.trueFalseContainer.makeGone()
        itemQuiz.customAnswerEditText.makeGone()


        val questionType =  when(lessonQuestion.questionType) {
            QUESTION_TYPE.MULTIPLE_CHOICE.type -> {
                itemQuiz.multipleChoiceContainer.makeVisible()
                setAnswersRecyclerView(questionNumber, lessonQuestion.answers)
            }
            QUESTION_TYPE.TRUE_FALSE.type -> {
                itemQuiz.trueFalseContainer.makeVisible()
            }
            QUESTION_TYPE.INPUT.type -> {
                itemQuiz.customAnswerEditText.makeVisible()
            }
            else -> {
                itemQuiz.multipleChoiceContainer.makeVisible()
                setAnswersRecyclerView(questionNumber, lessonQuestion.answers)
            }
        }
        itemQuiz.question_text.text = lessonQuestion.question
    }

    private fun setAnswersRecyclerView(questionNumber: Int, answers: List<String>) {
        val quizAnswersAdapter = QuizAnswersAdapter(answers)
        itemQuiz.multipleChoiceContainer.answersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = quizAnswersAdapter
        }
    }

    private fun getBundleValues() {
        parentProgram = args.parentProgram
        parentCourse = args.parentCourse
        lesson = args.parentLesson
    }

    private fun setListeners() {
        previousButton.setOnClickListener {
            selectedAnswers[questionNumber] = ""
            questionNumber -= 1
            setQuestion(questionNumber)
        }

        nextButton.setOnClickListener {
            selectedAnswers[questionNumber] = ""
            questionNumber += 1
            setQuestion(questionNumber)
        }
    }

    private fun setObservers() {
        homeViewModel.lessonQuestionsLiveData.observeFreshly(this) {
            lessons = it
            setQuestion(questionNumber)
        }

    }

    private fun getLessonQuestions() {
        val requestBody = createJsonRequestBody(LESSON_ID to 0)
        homeViewModel.getLessonQuestions(requestBody)
    }

    private fun saveProgress() {
        val requestBody = createJsonRequestBody(COURSE_ID to parentCourse?.id , LESSON_ID to lesson?.id, PROGRAM_ID to parentProgram?.id)
        homeViewModel.saveProgramProgress(requestBody)
    }

    private fun setViews() {

    }

}