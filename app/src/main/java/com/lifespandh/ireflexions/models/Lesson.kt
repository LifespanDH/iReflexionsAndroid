package com.lifespandh.ireflexions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson (

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

): Parcelable