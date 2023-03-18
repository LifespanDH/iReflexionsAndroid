package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName
import com.lifespandh.ireflexions.utils.network.*
import java.util.Date

data class User(

    @SerializedName(NAME)
    val name: String,

    @SerializedName(EMAIL)
    val email: String,

    @SerializedName(PHONE_NUMBER)
    val phoneNumber: String,

    @SerializedName(DOB)
    val dob: Date,

    @SerializedName(REGION)
    val region: String
)