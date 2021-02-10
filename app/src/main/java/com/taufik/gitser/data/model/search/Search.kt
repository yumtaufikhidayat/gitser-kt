package com.taufik.gitser.data.model.search

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
