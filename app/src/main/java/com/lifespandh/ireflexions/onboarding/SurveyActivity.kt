package com.lifespandh.ireflexions.onboarding

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseActivity
import com.lifespandh.ireflexions.models.SurveyQuestion
import com.lifespandh.ireflexions.models.SurveyResponse
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.android.synthetic.main.activity_survey.*

class SurveyActivity : BaseActivity() {

    private val onboardingViewModel by viewModels<OnboardingViewModel> { viewModelFactory }

    private var surveyQuestions: List<SurveyQuestion> = mutableListOf()
    private var position = 0
    private var seekBarProgress = 50

    private val surveyQuestionResponsesMap: MutableMap<SurveyQuestion, Int> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        init()
    }

    private fun init () {
        onboardingViewModel.getSurveyQuestions()
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        nextButton.setOnClickListener {
            surveyQuestionResponsesMap[surveyQuestions[position]] = seekBarProgress
            position += 1
            setQuestion()
        }

        prev_button.setOnClickListener {
            surveyQuestionResponsesMap[surveyQuestions[position]] = seekBarProgress
            position -= 1
            setQuestion()
        }

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                seekBarProgress = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    private fun setObservers() {
        onboardingViewModel.surveyQuestionsLiveData.observeFreshly(this) {
            surveyQuestions = it
            setQuestion()
        }

        onboardingViewModel.errorLiveData.observeFreshly(this) {
            toast(it)
        }
    }

    private fun setQuestion() {
        if (position >= surveyQuestions.size) {
            submitResponses()
            return
        }

        nextButton.text = "Next"
        prev_button.makeVisible()

        when(position) {
            0 -> {
                prev_button.makeGone()
            }
            surveyQuestions.size - 1 -> {
                nextButton.text = "Finish"
            }
            else -> {

            }
        }

        val surveyQuestion = surveyQuestions[position]
        seekBarProgress = surveyQuestionResponsesMap.get(surveyQuestion) ?: 50
        question_text.text = surveyQuestion.question
        Glide.with(this).load(surveyQuestion.image).into(question_image)
        seekBar.progress = seekBarProgress
    }

    private fun submitResponses() {
        val responses = mutableListOf<SurveyResponse>()
        for (response in surveyQuestionResponsesMap) {
            val question = response.key
            val value = response.value

            val surveyResponse = SurveyResponse(question, value)
            responses.add(surveyResponse)
        }

//        val requestBody = createJsonRequestBody(responses)
        onboardingViewModel.saveSurveyQuestions(responses)
    }
}