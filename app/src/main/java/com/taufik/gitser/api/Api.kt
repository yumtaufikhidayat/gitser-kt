package com.taufik.gitser.api

import com.taufik.gitser.BuildConfig
import com.taufik.gitser.data.model.detail.DetailSearchResponse
import com.taufik.gitser.data.model.repository.RepositoryResponse
import com.taufik.gitser.data.model.search.Search
import com.taufik.gitser.data.model.search.SearchResponse
import com.taufik.gitser.utils.Utils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(Utils.SEARCH_USERS)
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN_PAT}")
    fun searchUsers(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET(Utils.DETAIL_PROFILE_URL)
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN_PAT}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<DetailSearchResponse>

    @GET(Utils.FOLLOWERS_URL)
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN_PAT}")
    fun getFollowersUsers(
        @Path("username") username: String
    ): Call<ArrayList<Search>>

    @GET(Utils.FOLLOWING_URL)
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN_PAT}")
    fun getFollowingUsers(
        @Path("username") username: String
    ): Call<ArrayList<Search>>

    @GET(Utils.REPOSITORY_URL)
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN_PAT}")
    fun getRepository(
        @Path("username") username: String
    ): Call<ArrayList<RepositoryResponse>>
}