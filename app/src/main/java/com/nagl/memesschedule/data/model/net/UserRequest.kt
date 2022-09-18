package com.nagl.memesschedule.data.model.net

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String
)
