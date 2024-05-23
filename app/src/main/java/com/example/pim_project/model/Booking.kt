package com.example.pim_project.model

import com.google.gson.annotations.SerializedName

data class Booking (
    @SerializedName("_id")
    val id: String,

    @SerializedName("user")
    val user: User,

    @SerializedName("hardware")
    val hardware: Hardware,

    @SerializedName("date")
    val date: String,

    @SerializedName("time")
    val time: String
)