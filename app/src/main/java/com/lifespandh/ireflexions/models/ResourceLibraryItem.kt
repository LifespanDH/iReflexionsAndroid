package com.lifespandh.ireflexions.models

import com.google.gson.annotations.SerializedName

data class ResourceLibraryItem (

    @SerializedName("title")
    val title: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("resources")
    val resources: List<String>,
)