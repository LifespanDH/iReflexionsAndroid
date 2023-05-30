package com.lifespandh.ireflexions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProgramProgress (

    @SerializedName("course_number")
    val courseNumber: Int,

    @SerializedName("lesson_number")
    val lessonNumber: Int,

    @SerializedName("program_progress")
    val programProgress: Float,

    @SerializedName("course_progress")
    val courseProgress: Float,

): Parcelable