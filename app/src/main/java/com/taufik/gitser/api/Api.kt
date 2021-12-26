package com.taufik.gitser.api

import com.taufik.gitser.data.model.detail.DetailResponse
import com.taufik.gitser.data.model.repository.RepositoryResponse
import com.taufik.gitser.data.model.search.Search
import com.taufik.gitser.data.model.search.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(UrlEndpoint.SEARCH_USERS)
    @Headers("Authorization: token ${UrlEndpoint.GITHUB_TOKEN_PAT}")
    fun searchUsers(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET(UrlEndpoint.DETAIL_PROFILE_URL)
    @Headers("Authorization: token ${UrlEndpoint.GITHUB_TOKEN_PAT}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET(UrlEndpoint.FOLLOWERS_URL)
    @Headers("Authorization: token ${UrlEndpoint.GITHUB_TOKEN_PAT}")
    fun getFollowersUsers(
        @Path("username") username: String
    ): Call<ArrayList<Search>>

    @GET(UrlEndpoint.FOLLOWING_URL)
    @Headers("Authorization: token ${UrlEndpoint.GITHUB_TOKEN_PAT}")
    fun getFollowingUsers(
        @Path("username") username: String
    ): Call<ArrayList<Search>>

    @GET(UrlEndpoint.REPOSITORY_URL)
    @Headers("Authorization: token ${UrlEndpoint.GITHUB_TOKEN_PAT}")
    fun getRepository(
        @Path("username") username: String
    ): Call<ArrayList<RepositoryResponse>>

    @GET(UrlEndpoint.ALL_USERS)
    @Headers("Authorization: token ${UrlEndpoint.GITHUB_TOKEN_PAT}")
    fun getAllUsers() : Call<ArrayList<Search>>
}