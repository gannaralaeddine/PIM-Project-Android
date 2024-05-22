package com.example.pim_project.model

data class Booking (
    val user: User,
    val hardware: Hardware,
    val date: String,
    val time: String
)