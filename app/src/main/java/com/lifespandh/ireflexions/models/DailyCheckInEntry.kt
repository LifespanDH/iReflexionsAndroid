package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyCheckInEntry(

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
