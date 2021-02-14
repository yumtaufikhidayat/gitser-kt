package com.taufik.consumerapp

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
