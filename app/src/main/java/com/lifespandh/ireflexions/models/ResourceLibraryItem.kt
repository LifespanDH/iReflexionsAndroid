package com.lifespandh.ireflexions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResourceLibraryItem (

    @SerializedName("title")
    val title: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("resources")
    val resources: List<String>,
):Parcelable