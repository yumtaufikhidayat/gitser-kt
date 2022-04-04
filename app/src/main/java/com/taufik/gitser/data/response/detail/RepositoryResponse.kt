package com.taufik.gitser.data.response.detail

data class RepositoryResponse(
    val description: String,
    val html_url: String,
    val language: String,
    val name: String,
    val size: Int,
)