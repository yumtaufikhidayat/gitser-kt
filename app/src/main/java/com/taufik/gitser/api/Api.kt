package com.taufik.gitser.api

import com.taufik.gitser.api.UrlEndpoint.GITHUB_AUTH_TOKEN
import com.taufik.gitser.data.response.detail.DetailResponse
import com.taufik.gitser.data.response.detail.RepositoryResponse
import com.taufik.gitser.data.response.search.Search
import com.taufik.gitser.data.response.search.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(UrlEndpoint.ALL_USERS)
    @Headers(GITHUB_AUTH_TOKEN)
    fun getAllUsers() : Call<ArrayList<Search>>

    @GET(UrlEndpoint.SEARCH_USERS)
    @Headers(GITHUB_AUTH_TOKEN)
    fun searchUsers(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET(UrlEndpoint.DETAIL_PROFILE_URL)
    @Headers(GITHUB_AUTH_TOKEN)
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET(UrlEndpoint.FOLLOWING_URL)
    @Headers(GITHUB_AUTH_TOKEN)
    fun getFollowingUsers(
        @Path("username") username: String
    ): Call<ArrayList<Search>>

    @GET(UrlEndpoint.FOLLOWERS_URL)
    @Headers(GITHUB_AUTH_TOKEN)
    fun getFollowersUsers(
        @Path("username") username: String
    ): Call<ArrayList<Search>>

    @GET(UrlEndpoint.REPOSITORY_URL)
    @Headers(GITHUB_AUTH_TOKEN)
    fun getRepository(
        @Path("username") username: String
    ): Call<ArrayList<RepositoryResponse>>
}