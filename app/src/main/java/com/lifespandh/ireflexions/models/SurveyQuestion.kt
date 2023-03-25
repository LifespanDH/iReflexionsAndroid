package com.lifespandh.ireflexions.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class SurveyQuestion(

    @SerializedName("question")
    val question: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("options")
    val options: JsonObject

)