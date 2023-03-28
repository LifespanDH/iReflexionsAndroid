package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName

data class SurveyQuestion(

    @SerializedName("question")
    val question: String,

    @SerializedName("image")
    val image: String?,

    @SerializedName("id")
    val id: Int
)

data class SurveyResponse(

    @SerializedName("survey_question")
    val question: SurveyQuestion,

    @SerializedName("response")
    val response: Int

)