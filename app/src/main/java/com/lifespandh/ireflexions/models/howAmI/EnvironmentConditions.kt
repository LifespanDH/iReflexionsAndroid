package com.lifespandh.ireflexions.models.howAmI

class EnvironmentConditions{

    companion object {
    val defaultEnvironmentConditions=
        listOf(
            EnvironmentCondition(0, "Calm"),
            EnvironmentCondition(1, "Peaceful"),
            EnvironmentCondition(2, "Neutral"),
            EnvironmentCondition(3, "Stressed"),
            EnvironmentCondition(4, "Hectic"),
            EnvironmentCondition(5, "Other")
        )


    //to db and to server
    var customEnvironmentConditions = mutableListOf<EnvironmentCondition>()

    var totalEnvironmentConditions = defaultEnvironmentConditions + customEnvironmentConditions
}
}

data class EnvironmentCondition(
    val id: Int,
    val name: String
)

//to db and to server
data class EnvironmentConditionResult(

    val id: Int? = null,
    val name: String,
    val userId: String,
    val environmentId: Int,
    var dailyCheckInId: String,
    val isSelected: Boolean = false,
    val serverId:String = ""
)
