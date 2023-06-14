package com.lifespandh.ireflexions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Program (

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String?,

    @SerializedName("is_registered")
    val isRegistered: Boolean

//    @SerializedName("courses")
//    val courses: List<Course>

): Parcelable