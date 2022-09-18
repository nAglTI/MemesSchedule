package com.nagl.memesschedule.data.model

import com.google.gson.annotations.SerializedName

data class User(
//    val accountExpirationDate: String = "",
//    val accountLockoutTime: String = "",
    @SerializedName("description")
    val description: String,
    @SerializedName("displayName")
    val displayName: String,
    @SerializedName("emailAddress")
    val email: String,
    @SerializedName("guid")
    val guid: String,
)
