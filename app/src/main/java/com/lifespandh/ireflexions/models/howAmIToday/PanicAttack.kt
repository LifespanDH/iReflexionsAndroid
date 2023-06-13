package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName
import java.sql.Time

data class PanicAttack(

    @SerializedName("time")
    val time: Time,

    @SerializedName("intensity")
    val intensity: Int,

    @SerializedName("trigger")
    val trigger: PanicTrigger?,

    @SerializedName("symptoms")
    val symptoms: List<PanicSymptom>
)

data class PanicTrigger(

    @SerializedName("name")
    val name: String,

    @SerializedName("user_created")
    val userCreated: Boolean
)

data class PanicSymptom(

    @SerializedName("name")
    val name: String,

    @SerializedName("user_created")
    val userCreated: Boolean
)