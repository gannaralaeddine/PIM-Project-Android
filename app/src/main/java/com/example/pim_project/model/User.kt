package com.example.pim_project.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    var _id:String,

    @SerializedName("email")
    var email:String,

    @SerializedName("firstName")
    var firstName:String,

    @SerializedName("espritIdentifier")
    var espritIdentifier:String,

    @SerializedName("classroom")
    var classroom: String,

    @SerializedName("isVerified")
    var isVerified: Boolean
)