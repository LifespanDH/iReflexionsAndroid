package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName

data class WhatsHappening(
    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("user_created")
    val userCreated: Boolean = false,

    @SerializedName("panic_attack")
    val panicAttack: Boolean  = false,

    @SerializedName("id")
    val id: Int = -1
    ) {

    companion object {
        fun createNew(): WhatsHappening {
            return WhatsHappening("Create New", "", false, panicAttack = false)
        }
    }
}