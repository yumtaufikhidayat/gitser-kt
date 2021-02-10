package com.taufik.gitser.data.model.detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailSearchResponse(

    @Expose
    @SerializedName("login")
    val login: String,
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("node_id")
    val node_id: String,
    @Expose
    @SerializedName("avatar_url")
    val avatar_url: String,
    @Expose
    @SerializedName("gravatar_id")
    val gravatar_id: String,
    @Expose
    @SerializedName("url")
    val url: String,
    @Expose
    @SerializedName("html_url")
    val html_url: String,
    @Expose
    @SerializedName("followers_url")
    val followers_url: String,
    @Expose
    @SerializedName("following_url")
    val following_url: String,
    @Expose
    @SerializedName("gists_url")
    val gists_url: String,
    @Expose
    @SerializedName("starred_url")
    val starred_url: String,
    @Expose
    @SerializedName("subscriptions_url")
    val subscriptions_url: String,
    @Expose
    @SerializedName("organizations_url")
    val organizations_url: String,
    @Expose
    @SerializedName("repos_url")
    val repos_url: String,
    @Expose
    @SerializedName("events_url")
    val events_url: String,
    @Expose
    @SerializedName("received_events_url")
    val received_events_url: String,
    @Expose
    @SerializedName("type")
    val type: String,
    @Expose
    @SerializedName("site_admin")
    var site_admin: Boolean,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("company")
    val company: String,
    @Expose
    @SerializedName("blog")
    val blog: String,
    @Expose
    @SerializedName("location")
    val location: String,
    @Expose
    @SerializedName("bio")
    val bio: String,
    @Expose
    @SerializedName("public_repos")
    var public_repos: Int,
    @Expose
    @SerializedName("public_gists")
    var public_gists: Int,
    @Expose
    @SerializedName("followers")
    var followers: Int,
    @Expose
    @SerializedName("following")
    var following: Int,
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String
)