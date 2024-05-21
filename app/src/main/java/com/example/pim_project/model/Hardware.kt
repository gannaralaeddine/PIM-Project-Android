package com.example.pim_project.model

import com.google.gson.annotations.SerializedName

data class Hardware(

    @SerializedName("_id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("reference")
    val reference: String,

    @SerializedName("brand")
    val brand: String,

    @SerializedName("model")
    val model: String,

    @SerializedName("lab")
    val lab: String,

    @SerializedName("isAvailable")
    val isAvailable: Boolean,

    @SerializedName("dispoDates")
    val dispoDates: List<DispoDate>
)