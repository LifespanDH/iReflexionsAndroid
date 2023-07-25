package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName

//data class MonthlyReport(
//
//
//)

data class Emotion(

    @SerializedName("count")
    var count: Int,

    @SerializedName("color")
    val color: String
)

typealias EmotionData = Pair<Emotion, Map<String, Emotion>>