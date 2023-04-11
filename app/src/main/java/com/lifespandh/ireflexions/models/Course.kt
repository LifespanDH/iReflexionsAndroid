package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName

data class Course (

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("lessons")
    val lessons: List<Lesson>

)