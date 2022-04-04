package com.taufik.gitser.data.response.detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @Expose
    @SerializedName("login")
    val login: String,
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("node_id")
    val nodeId: String,
    @Expose
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @Expose
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @Expose
    @SerializedName("url")
    val url: String,
    @Expose
    @SerializedName("html_url")
    val htmlUrl: String,
    @Expose
    @SerializedName("followers_url")
    val followersUrl: String,
    @Expose
    @SerializedName("following_url")
    val followingUrl: String,
    @Expose
    @SerializedName("gists_url")
    val gistsUrl: String,
    @Expose
    @SerializedName("starred_url")
    val starredUrl: String,
    @Expose
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,
    @Expose
    @SerializedName("organizations_url")
    val organizationsUrl: String,
    @Expose
    @SerializedName("repos_url")
    val reposUrl: String,
    @Expose
    @SerializedName("events_url")
    val eventsUrl: String,
    @Expose
    @SerializedName("received_events_url")
    val receivedEventsUrl: String,
    @Expose
    @SerializedName("type")
    val type: String,
    @Expose
    @SerializedName("site_admin")
    var siteAdmin: Boolean,
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
    var publicRepos: Int,
    @Expose
    @SerializedName("public_gists")
    var publicGists: Int,
    @Expose
    @SerializedName("followers")
    var followers: Int,
    @Expose
    @SerializedName("following")
    var following: Int,
    @Expose
    @SerializedName("created_at")
    val createdAt: String,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: String
)