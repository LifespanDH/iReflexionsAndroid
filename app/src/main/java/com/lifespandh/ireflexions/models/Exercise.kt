package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName

data class Exercise(

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("unity_id")
    val unityId: String

)