package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyCheckInEntry(

    @SerializedName("id")
    val id: Int,

//    @SerializedName("traitCategory")
//    val traitCategoryResults: List<TraitCategoryResult>,

    @SerializedName("sleep_duration")
    val sleepDuration: Int,

    @SerializedName("sleep_quality")
    val sleepQuality: Int,

    @SerializedName("movement")
    val movement: Int,

    @SerializedName("entry")
    val entry: String,

    @SerializedName("date")
    val date: Date
)
