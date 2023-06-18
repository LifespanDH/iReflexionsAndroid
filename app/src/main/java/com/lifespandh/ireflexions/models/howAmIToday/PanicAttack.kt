package com.lifespandh.ireflexions.models.howAmIToday

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.sql.Time

data class PanicAttack(

    @SerializedName("time")
    var time: String,

    @SerializedName("intensity")
    val intensity: Int,

    @SerializedName("triggers")
    val triggers: List<PanicTrigger>,

    @SerializedName("symptoms")
    val symptoms: List<PanicSymptom>
)

@Parcelize
data class PanicTrigger(

    @SerializedName("name")
    val name: String,

    @SerializedName("user_created")
    val userCreated: Boolean,

    @SerializedName("id")
    val id: Int = -1
): Parcelable {

    companion object {
        fun other(): PanicTrigger {
            return PanicTrigger("Other", false)
        }
    }
}

@Parcelize
data class PanicSymptom(

    @SerializedName("name")
    val name: String,

    @SerializedName("user_created")
    val userCreated: Boolean,

    @SerializedName("id")
    val id: Int = -1
): Parcelable {

    companion object {
        fun other(): PanicSymptom {
            return PanicSymptom("Other", false)
        }
    }
}