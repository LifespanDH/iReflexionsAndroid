package com.lifespandh.ireflexions.models.howAmI

import com.google.gson.annotations.SerializedName

data class EnvironmentCondition(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("is_custom")
    val isCustom : Boolean
)
