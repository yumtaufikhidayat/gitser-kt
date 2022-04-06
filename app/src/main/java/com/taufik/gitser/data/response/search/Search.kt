package com.taufik.gitser.data.response.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Search(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String
): Parcelable
