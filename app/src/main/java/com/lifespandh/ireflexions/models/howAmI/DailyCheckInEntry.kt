package com.lifespandh.ireflexions.models.howAmI

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyCheckInEntry(

    @SerializedName("id")
    val id: Int,

    @SerializedName("traitCategory")
    val traitCategoryResults: List<TraitCategoryResult>,

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

//var id: String = ""
//var traitCategoryResults = mutableListOf<TraitCategoryResult>()
//var traitResults = mutableListOf<TraitResult>()
//var happeningResults = mutableListOf<HappeningResult>()
//var envConditions = mutableListOf<EnvironmentConditionResult>()
//var sleepDuration: Int = 8 // range 0 to 13
//var sleepQuality: Int = 4 // range from 0 to 5
//var journalEntry: String = ""
//var movement: Int = 2
//var panicAttack: PanicAttack? = null
//var date: String = ""
