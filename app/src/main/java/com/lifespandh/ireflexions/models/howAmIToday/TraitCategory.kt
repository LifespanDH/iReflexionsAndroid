package com.lifespandh.ireflexions.models.howAmIToday

import com.google.gson.annotations.SerializedName

data class TraitCategory(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("color")
    val color: String,

    @SerializedName("trait_sub_categories")
    val traits: List<TraitSubCategory>
)

data class TraitSubCategory(

    @SerializedName("name")
    val name: String,

    @SerializedName("color")
    val color: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("trait_id")
    val traitId: Int
)