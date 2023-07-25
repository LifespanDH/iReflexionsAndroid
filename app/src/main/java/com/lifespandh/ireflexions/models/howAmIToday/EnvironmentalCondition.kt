package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName

data class EnvironmentalCondition(

    @SerializedName("name")
    val name: String,

    @SerializedName("user_created")
    val userCreated: Boolean,

    @SerializedName("id")
    val id: Int = -1
) {

    companion object {

        fun other(): EnvironmentalCondition {
            return EnvironmentalCondition("Other", false)
        }
    }
}