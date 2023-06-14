package com.lifespandh.ireflexions.models

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

class PanicAttackTriggers {
    companion object {
        val defaultPanicAttackTriggers =  listOf(
            PanicAttackTrigger(0, "Interpersonal Conflict"),
            PanicAttackTrigger(1, "Sensory Stimulus"),
            PanicAttackTrigger(2, "Negative\n Feedback\n/Criticism"),
            PanicAttackTrigger(3, "Overwhelmed"),
            PanicAttackTrigger(4, "Rejection"),
            PanicAttackTrigger(5, "Made a Mistake"),
            PanicAttackTrigger(6, "Other")
        )

        //to db and to server
        var customPanicAttackTriggers = mutableListOf<PanicAttackTrigger>()

        var totalEnvironmentConditions = defaultPanicAttackTriggers + customPanicAttackTriggers
    }
}

data class PanicAttackTrigger(
    val id: Int,
    val name: String
)

//to db and to server
data class PanicAttackTriggerResult(
    val id: Int ?= null,
    val userId: String,
    val name: String,
    val triggerId: Int,
    val panicAttackId: String,
    val isSelected: Boolean = false,
    val serverId: String = ""
)

class PanicAttackSymptoms {
    companion object {
        val defaultPanicAttackSymptoms = listOf(
            PanicAttackSymptom(0, "Fear of\nfainting"),
            PanicAttackSymptom(1, "Fear of dying"),
            PanicAttackSymptom(2, "Fear of\ngoing crazy"),
            PanicAttackSymptom(3, "Dizziness"),
            PanicAttackSymptom(4, "Heart\nPalpitations"),
            PanicAttackSymptom(5, "Sweating"),

            PanicAttackSymptom(6, "Cold Chills"),
            PanicAttackSymptom(7, "Cold Sweat"),
            PanicAttackSymptom(8, "Tingling"),
            PanicAttackSymptom(9, "Tremor"),
            PanicAttackSymptom(10, "Diarrhea"),
            PanicAttackSymptom(11, "Nausea"),

            PanicAttackSymptom(12, "Shortness\nof Breath"),
            PanicAttackSymptom(13, "Tightness"),
            PanicAttackSymptom(14, "Chest Pain"),
            PanicAttackSymptom(15, "Derealization"),
            PanicAttackSymptom(16, "Fear of Anxiety "),
            PanicAttackSymptom(17, "Other")
        )

        //to db and to server
        var customPanicAttackSymptoms = mutableListOf<PanicAttackSymptom>()

        var totalEnvironmentConditions = defaultPanicAttackSymptoms + customPanicAttackSymptoms
    }
}

data class PanicAttackSymptom(
    val id: Int,
    val name: String
)

//to db and to server
data class PanicAttackSymptomResult(
    val id: Int ?= null,
    val userId: String,
    val name: String,
    val triggerId: Int,
    val panicAttackId: String,
    val isSelected: Boolean = false,
    val serverId: String = ""
)