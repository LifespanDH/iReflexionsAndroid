package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName

data class Lesson (

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String
)