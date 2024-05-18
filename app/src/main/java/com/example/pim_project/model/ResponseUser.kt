package com.example.pim_project.model

data class ResponseUser(
    var user: User,
    var token: String,
    var message: String,
//    var myHardwareList: List<Hardware>
)
