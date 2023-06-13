package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName

data class HowAmITodayData(

    @SerializedName("trait_categories")
    val traitCategories: List<TraitCategory>,

    @SerializedName("whats_happening")
    val whatsHappening: List<WhatsHappening>,

    @SerializedName("environmental_conditions")
    val environmentalConditions: List<EnvironmentalCondition>,

    @SerializedName("panic_triggers")
    val panicTriggers: List<PanicTrigger>,

    @SerializedName("panic_symptoms")
    val panicSymptoms: List<PanicSymptom>
)