package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName

data class TraitCategory(

    @SerializedName("name")
    val name: String,

    @SerializedName("color")
    val color: String,

    @SerializedName("trait_sub_categories")
    val subCategories: List<TraitSubCategory>
)

data class TraitSubCategory(

    @SerializedName("name")
    val name: String,

    @SerializedName("color")
    val color: String
)