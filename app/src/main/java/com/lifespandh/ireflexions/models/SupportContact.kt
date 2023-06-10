package com.lifespandh.ireflexions.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SupportContact (

    @SerializedName("id")
    val id: Int = -1,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone_number")
    val phoneNumber: String,

    @SerializedName("image")
    val image: String?,

): Parcelable