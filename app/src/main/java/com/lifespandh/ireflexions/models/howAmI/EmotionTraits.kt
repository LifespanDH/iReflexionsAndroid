package com.lifespandh.ireflexions.models.howAmI

import com.lifespandh.ireflexions.R

class EmotionTraits {

    companion object {

        val calmTraitCategory = TraitCategory(0, "Calm", R.color.calm).apply {
            traits.add(Trait(0,"Nurturing", R.color.calm0).apply { level = 0 })
            traits.add(Trait(1, "Intimate", R.color.calm1).apply { level = 1 })
            traits.add(Trait(2, "Thoughtful", R.color.calm2).apply { level = 2 })
            traits.add(Trait(3, "Loving", R.color.calm3).apply { level = 3 })
            traits.add(Trait(4, "Trusting", R.color.calm4).apply { level = 4 })
            traits.add(Trait(5, "Content", R.color.calm5).apply { level = 5 })
        }

        val madTraitCategory = TraitCategory(1, "Mad", R.color.mad).apply {
            traits.add(Trait(10, "Critical", R.color.mad0).apply { level = 0 })
            traits.add(Trait(11, "Hateful", R.color.mad1).apply { level = 1 })
            traits.add(Trait(12,"Selfish", R.color.mad2).apply { level = 2 })
            traits.add(Trait(13,"Hurt", R.color.mad3).apply { level = 3 })
            traits.add(Trait(14,"Hostile", R.color.mad4).apply { level = 4 })
            traits.add(Trait(15,"Angry", R.color.mad5).apply { level = 5 })
        }

        val scaredTraitCategory = TraitCategory(2, "Scared", R.color.scared).apply {
            traits.add(Trait(20, "Confused", R.color.scared0).apply { level = 0 })
            traits.add(Trait(21, "Rejected", R.color.scared1).apply { level = 1 })
            traits.add(Trait(22, "Overwhelmed", R.color.scared2).apply { level = 2 })
            traits.add(Trait(23, "Anxious", R.color.scared3).apply { level = 3 })
            traits.add(Trait(24, "Insecure", R.color.scared4).apply { level = 4 })
            traits.add(Trait(25, "Helpless", R.color.scared5).apply { level = 5 })
        }

        val powerfulTraitCategory = TraitCategory(3,"Powerful", R.color.powerful).apply {
            traits.add(Trait(30, "Aware", R.color.powerful0).apply { level = 0 })
            traits.add(Trait(31, "Proud", R.color.powerful1).apply { level = 1 })
            traits.add(Trait(32, "Respected", R.color.powerful2).apply { level = 2 })
            traits.add(Trait(33, "Appreciated", R.color.powerful3).apply { level = 3 })
            traits.add(Trait(34, "Important", R.color.powerful4).apply { level = 4 })
            traits.add(Trait(35, "Confident", R.color.powerful5).apply { level = 5 })
        }

        val joyfulTraitCategory = TraitCategory(4, "Joyful", R.color.joyful).apply {
            traits.add(Trait(40, "Energetic", R.color.joyful0).apply { level = 0 })
            traits.add(Trait(41, "Playful", R.color.joyful1).apply { level = 1 })
            traits.add(Trait(42, "Hopeful", R.color.joyful2).apply { level = 2 })
            traits.add(Trait(43, "Creative", R.color.joyful3).apply { level = 3 })
            traits.add(Trait(44, "Cheerful", R.color.joyful4).apply { level = 4 })
            traits.add(Trait(45, "Excited", R.color.joyful5).apply { level = 5 })
        }

        val sadTraitCategory = TraitCategory(5, "Sad", R.color.sad).apply {
            traits.add(Trait(50, "Tired", R.color.sad0).apply { level = 0 })
            traits.add(Trait(51, "Bored", R.color.sad1).apply { level = 1 })
            traits.add(Trait(52, "Lonely", R.color.sad2).apply { level = 2 })
            traits.add(Trait(53, "Depressed", R.color.sad3).apply { level = 3 })
            traits.add(Trait(54, "Ashamed", R.color.sad4).apply { level = 4 })
            traits.add(Trait(55, "Guilty", R.color.sad).apply { level = 5 })
        }

        val categories = listOf(
            calmTraitCategory,
            madTraitCategory,
            scaredTraitCategory,
            powerfulTraitCategory,
            joyfulTraitCategory,
            sadTraitCategory
        )
    }
}

data class TraitCategory(
    val id: Int,
    val name: String,
    val color: Int
) {
    val traits = mutableListOf<Trait>()
}

data class Trait(
    val id: Int,
    val name: String,
    val color: Int
) {
    var level: Int = 0
}

//to db
data class TraitCategoryResult(
    val id: Int ?= null,
    val userId: String,
    val traitCategoryId: Int,
    var dailyCheckInId: String,
    var isSelected: Boolean = false
)

//to db
data class TraitResult(
    val id: Int ?= null,
    val userId: String,
    val traitId: Int,
    var dailyCheckInId: String,
    var isSelected: Boolean = false
)