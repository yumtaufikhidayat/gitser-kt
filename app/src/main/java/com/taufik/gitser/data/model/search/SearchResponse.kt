package com.taufik.gitser.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items")
    val items: ArrayList<Search>
)
