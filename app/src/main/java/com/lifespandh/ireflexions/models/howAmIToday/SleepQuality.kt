package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName

data class SleepQuality(

    @SerializedName("time")
    val time: Int,

    @SerializedName("quality")
    val quality: Int
)