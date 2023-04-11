package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName

data class Program (

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val img: String,

    @SerializedName("courses")
    val courses: List<Course>
)