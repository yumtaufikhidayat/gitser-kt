package com.taufik.gitser.data.response.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items")
    val items: ArrayList<Search>
)
