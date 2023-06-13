package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName

data class EnvironmentalCondition(

    @SerializedName("name")
    val name: String,

    @SerializedName("user_created")
    val userCreated: Boolean
) {

    companion object {

        fun other(): EnvironmentalCondition {
            return EnvironmentalCondition("Other", false)
        }
    }
}