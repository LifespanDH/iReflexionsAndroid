package com.lifespandh.ireflexions.models.howAmI

import com.lifespandh.ireflexions.R

class Happenings {
    companion object {

        val defaultHappenings = arrayListOf<Happening>()

        //to db and to server
        var customHappenings = mutableListOf<Happening>()

        var totalHappenings = defaultHappenings + customHappenings

        var count = 15
        fun getHappening(name: String, serverId: String, isCustom: Boolean): Happening {
            return when (name) {
                "Out of the House" -> Happening(
                    0,
                    serverId,
                    "Out of the House",
                    isCustom,
                    R.drawable.ic_leave
                )
                "Driving" -> Happening(
                    1,
                    serverId,
                    "Driving",
                    isCustom, R.drawable.ic_car
                )
                "Shopping" -> Happening(2, serverId, "Shopping", isCustom, R.drawable.ic_shopping)
                "Public Transport" -> Happening(
                    3,
                    serverId,
                    "Public Transport",
                    isCustom,
                    R.drawable.ic__bus
                )
                "Not Enough Sleep" -> Happening(
                    4,
                    serverId,
                    "Not Enough Sleep",
                    isCustom,
                    R.drawable.ic_bed
                )
                "Sick" -> Happening(5, serverId, "Sick", isCustom, R.drawable.ic_health)
                "Sex" -> Happening(6, serverId, "Sex", isCustom, R.drawable.ic_kiss)
                "PMS" -> Happening(7, serverId, "PMS", isCustom, R.drawable.ic_blooddrop)
                "Relationship Issues" -> Happening(
                    8, serverId,
                    "Relationship Issues",
                    isCustom,
                    R.drawable.ic_heartbreak
                )
                "Travel" -> Happening(9, serverId, "Travel", isCustom, R.drawable.ic_plane)
                "Conflict" -> Happening(10, serverId, "Conflict", isCustom, R.drawable.ic_conflict)
                "Panic Attack!" -> Happening(
                    11,
                    serverId,
                    "Panic Attack!",
                    isCustom,
                    R.drawable.ic_panic
                )
                "Crowds" -> Happening(12, serverId, "Crowds", isCustom, R.drawable.ic_crowd)
                "Obsessive Thoughts" -> Happening(
                    13, serverId,
                    "Obsessive Thoughts",
                    isCustom,
                    R.drawable.ic_obsessivethoughts
                )
                "Self Care" -> Happening(
                    14,
                    serverId,
                    "Self Care",
                    isCustom,
                    R.drawable.ic_selfcare
                )

                // Need to do this
//                WhatsHappeningAdapter.CREATE_NEW_TEXT_CONST -> Happening(
//                    15,
//                    serverId,
//                    WhatsHappeningAdapter.CREATE_NEW_TEXT_CONST,
//                    false,
//                    R.drawable.ic_create_new
//                )
                else -> {
                    count++
                    Happening(count, serverId, name, isCustom, R.drawable.custom_button)
                }
            }
        }
    }
}

data class Happening(
    val id: Int,
    var serverId: String,
    val name: String,
    val isCustom: Boolean,
    val image: Int? = R.drawable.custom_button
)

data class HappeningItem(
    val id: Int,
    val serverId: String,
    val name: String,
    val isCustom: Boolean,
    val image: Int? = R.drawable.custom_button
)

data class HappeningResult(
    val id: Int? = null,
    val name: String = "",
    val userId: String = "",
    val happeningId: Int,
    var happeningIdStr: String = "",
    var dailyCheckInId: String = "",
    val isSelected: Boolean = false,
    var serverId: String = ""
)