package com.lifespandh.ireflexions.models.howAmIToday

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.sql.Time

data class PanicAttack(

    @SerializedName("time")
    val time: Time,

    @SerializedName("intensity")
    val intensity: Int,

    @SerializedName("trigger")
    val trigger: PanicTrigger?,

    @SerializedName("symptoms")
    val symptoms: List<PanicSymptom>
)

@Parcelize
data class PanicTrigger(

    @SerializedName("name")
    val name: String,

    @SerializedName("user_created")
    val userCreated: Boolean
): Parcelable

@Parcelize
data class PanicSymptom(

    @SerializedName("name")
    val name: String,

    @SerializedName("user_created")
    val userCreated: Boolean
): Parcelable