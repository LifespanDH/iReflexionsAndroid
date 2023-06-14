package com.lifespandh.ireflexions.models

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class LessonQuestion (

    @SerializedName("question")
    val question: String,

    @SerializedName("question_type")
    val questionType: Int,

    @SerializedName("answers")
    val answers: List<String>,

    @SerializedName("correct_answer")
    val correctAnswer: Int,

    @SerializedName("id")
    val id: Int

)

enum class QUESTION_TYPE (val type: Int) {

    MULTIPLE_CHOICE(1),
    TRUE_FALSE(2),
    INPUT(3);

    fun getQuestionType(type:Int): QUESTION_TYPE {
        return when(type) {
            MULTIPLE_CHOICE.type -> MULTIPLE_CHOICE
            TRUE_FALSE.type -> TRUE_FALSE
            INPUT.type -> INPUT
            else -> {MULTIPLE_CHOICE}
        }
    }
}
