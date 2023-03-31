package com.lifespandh.ireflexions.models.howAmI

import java.util.*


class PanicAttack {
    var triggerResults = mutableListOf<PanicAttackTriggerResult>()
    var symptomResults = mutableListOf<PanicAttackSymptomResult>()
    var time: Date = Calendar.getInstance().time
    var intensity = 0 // 0 to 5
}

data class
DataPanicAttack(
    var id: String,
    var time: Date = Calendar.getInstance().time,
    var intensity: Int = 0 // 0 to 5
)

data class PanicAttackItems(
    val panicAttack: DataPanicAttack,

    var triggerResults: List<PanicAttackTriggerResult>,

    var symptomResults: List<PanicAttackSymptomResult>
)

class PanicAttackTriggers {
    companion object {

        var defaultPanicAttackTriggers = arrayListOf<PanicAttackTrigger>()

        //to db and to server
        var customPanicAttackTriggers = mutableListOf<PanicAttackTrigger>()

        var totalPanicAttackTriggers = defaultPanicAttackTriggers + customPanicAttackTriggers

        var count = 6
        fun getPanicAttackTriggers(
            name: String,
            serverId: String,
            isCustom: Boolean
        ): PanicAttackTrigger {
            return when (name) {
                "Interpersonal Conflict" -> PanicAttackTrigger(
                    0,
                    serverId,
                    "Interpersonal Conflict",
                    isCustom
                )
                "Sensory Stimulus" -> PanicAttackTrigger(
                    1,
                    serverId,
                    "Sensory Stimulus",
                    isCustom
                )
                "Negative\n Feedback\n/Criticism" -> PanicAttackTrigger(
                    2,
                    serverId,
                    "Negative\n Feedback\n/Criticism",
                    isCustom
                )
                "Overwhelmed" -> PanicAttackTrigger(
                    3,
                    serverId,
                    "Overwhelmed",
                    isCustom
                )
                "Rejection" -> PanicAttackTrigger(
                    4,
                    serverId,
                    "Rejection",
                    isCustom
                )
                "Made a Mistake" -> PanicAttackTrigger(
                    5,
                    serverId,
                    "Made a Mistake",
                    isCustom
                )
//                EnvironmentConditionsAdapter.OTHER_TEXT_CONST -> PanicAttackTrigger(
//                    6,
//                    serverId,
//                    name,
//                    isCustom
//                )
                else -> {
                    count++
                    PanicAttackTrigger(count, serverId, name, isCustom)
                }
            }
        }
    }
}

data class PanicAttackTrigger(
    val id: Int,
    val serverId: String,
    val name: String,
    val isCustom: Boolean
)

data class PanicAttackTriggerResult(
    val id: Int? = null,
    val userId: String,
    val name: String,
    val triggerId: Int,
    val panicAttackId: String,
    val isSelected: Boolean = false,
    var serverId: String = ""
)

class PanicAttackSymptoms {
    companion object {

        val defaultPanicAttackSymptoms = arrayListOf<PanicAttackSymptom>()

        //to db and to server
        var customPanicAttackSymptoms = mutableListOf<PanicAttackSymptom>()

        var totalPanicAttackSymptoms = defaultPanicAttackSymptoms + customPanicAttackSymptoms

        var count = 17
        fun getPanicAttackSymptoms(name: String, serverId: String, isCustom : Boolean): PanicAttackSymptom {
            return when (name) {
                "Fear of\nfainting" -> PanicAttackSymptom(
                    0,
                    serverId,
                    "Fear of\nfainting",
                    isCustom
                )
                "Fear of dying" -> PanicAttackSymptom(
                    1,
                    serverId,
                    "Fear of dying",
                    isCustom
                )
                "Fear of\ngoing crazy" -> PanicAttackSymptom(
                    2,
                    serverId,
                    "Fear of\ngoing crazy",
                    isCustom
                )
                "Dizziness" -> PanicAttackSymptom(
                    3,
                    serverId,
                    "Dizziness",
                    isCustom
                )
                "Heart\nPalpitations" -> PanicAttackSymptom(
                    4,
                    serverId,
                    "Heart\nPalpitations",
                    isCustom
                )
                "Sweating" -> PanicAttackSymptom(
                    5,
                    serverId,
                    "Sweating",
                    isCustom
                )
                "Cold Chills" -> PanicAttackSymptom(
                    6,
                    serverId,
                    "Cold Chills",
                    isCustom
                )
                "Cold Sweat" -> PanicAttackSymptom(
                    7,
                    serverId,
                    "Cold Sweat",
                    isCustom
                )
                "Tingling" -> PanicAttackSymptom(
                    8,
                    serverId,
                    "Tingling",
                    isCustom
                )
                "Tremor" -> PanicAttackSymptom(
                    9,
                    serverId,
                    "Tremor",
                    isCustom
                )
                "Diarrhea" -> PanicAttackSymptom(
                    10,
                    serverId,
                    "Diarrhea",
                    isCustom
                )
                "Nausea" -> PanicAttackSymptom(
                    11,
                    serverId,
                    "Nausea",
                    isCustom
                )
                "Shortness\nof Breath" -> PanicAttackSymptom(
                    12,
                    serverId,
                    "Shortness\nof Breath",
                    isCustom
                )
                "Tightness" -> PanicAttackSymptom(
                    13,
                    serverId,
                    "Tightness",
                    isCustom
                )
                "Chest Pain" -> PanicAttackSymptom(
                    14,
                    serverId,
                    "Chest Pain",
                    isCustom
                )
                "Derealization" -> PanicAttackSymptom(
                    15,
                    serverId,
                    "Derealization",
                    isCustom
                )
                "Fear of Anxiety" -> PanicAttackSymptom(
                    16,
                    serverId,
                    "Fear of Anxiety",
                    isCustom
                )
//                EnvironmentConditionsAdapter.OTHER_TEXT_CONST -> PanicAttackSymptom(
//                    17,
//                    serverId,
//                    name,
//                    isCustom
//                )
                else -> {
                    count++
                    PanicAttackSymptom(count, serverId, name, isCustom)
                }
            }
        }
    }
}

data class PanicAttackSymptom(
    val id: Int,
    val serverId: String,
    val name: String,
    val isCustom : Boolean
)

data class PanicAttackSymptomResult(
    val id: Int? = null,
    val userId: String,
    val name: String,
    val symptomId: Int,
    val panicAttackId: String,
    val isSelected: Boolean = false,
    var serverId: String = ""
)