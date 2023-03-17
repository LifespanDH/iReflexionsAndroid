package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName

data class Token(

    @SerializedName("token")
    val token: String,

    @SerializedName("refresh")
    val refresh: String

)