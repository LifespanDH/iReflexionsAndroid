package com.lifespandh.ireflexions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Course (

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("image")
<<<<<<< Updated upstream
    val image: String?
=======
    val image: String,

//    @SerializedName("lessons")
//    val lessons: List<Lesson>
>>>>>>> Stashed changes

): Parcelable