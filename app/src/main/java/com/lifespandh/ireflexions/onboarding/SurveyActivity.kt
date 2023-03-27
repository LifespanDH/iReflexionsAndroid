package com.lifespandh.ireflexions.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseActivity
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.android.synthetic.main.activity_survey.*

class SurveyActivity : BaseActivity() {

    private val onboardingViewModel by viewModels<OnboardingViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        init()
    }

    fun init() {
        onboardingViewModel.getSurveyQuestions()
        setListeners()
        setObservers()
    }

    private fun setListeners() {

    }

    private fun setObservers() {
        onboardingViewModel.surveyQuestionsLiveData.observeFreshly(this) { list ->
            Log.d("response", "$list")

        }

        onboardingViewModel.errorLiveData.observeFreshly(this) {
            toast(it)
        }
    }
}