package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName

data class SupportContact (

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone_number")
    val phoneNumber: String,

    @SerializedName("image")
    val image: String,
)