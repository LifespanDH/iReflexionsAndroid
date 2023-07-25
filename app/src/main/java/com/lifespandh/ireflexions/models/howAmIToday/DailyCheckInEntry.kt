package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyCheckInEntry(

    @SerializedName("id")
    val id: Int = -1,

    @SerializedName("traits")
    val userTraits: List<UserTrait>,

    @SerializedName("whats_happening")
    val whatsHappening: List<WhatsHappening>,

    @SerializedName("panic_attack")
    val panicAttack: PanicAttack?,

    @SerializedName("environmental_conditions")
    val environmentalConditions: List<EnvironmentalCondition>,

    @SerializedName("movement")
    val movement: Int,

    @SerializedName("sleep_quality")
    val sleepQuality: SleepQuality,

    @SerializedName("journal_entry")
    val journalEntry: String,

    @SerializedName("date_time")
    val dateTime: String,
)

data class WeeklyReport(

    @SerializedName("date")
    val date: String,

    @SerializedName("daily_entries")
    val dailyEntries: List<DailyCheckInEntry>
)