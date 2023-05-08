package com.lifespandh.ireflexions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson (

    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("heading")
    val heading: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("image")
    val image: String,
): Parcelable