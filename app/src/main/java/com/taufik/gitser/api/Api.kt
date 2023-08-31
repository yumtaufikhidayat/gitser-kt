package com.taufik.gitser.api

import com.taufik.gitser.data.response.detail.DetailResponse
import com.taufik.gitser.data.response.detail.RepositoryResponse
import com.taufik.gitser.data.response.search.Search
import com.taufik.gitser.data.response.search.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(UrlEndpoint.ALL_USERS)
    fun getAllUsers() : Call<ArrayList<Search>>

    @GET(UrlEndpoint.SEARCH_USERS)
    fun searchUsers(
        @Query(UrlEndpoint.QUERY_Q) query: String
    ): Call<SearchResponse>

    @GET(UrlEndpoint.DETAIL_PROFILE_URL)
    fun getDetailUsers(
        @Path(UrlEndpoint.QUERY_USERNAME) username: String
    ): Call<DetailResponse>

    @GET(UrlEndpoint.FOLLOWING_URL)
    fun getFollowingUsers(
        @Path(UrlEndpoint.QUERY_USERNAME) username: String
    ): Call<ArrayList<Search>>

    @GET(UrlEndpoint.FOLLOWERS_URL)
    fun getFollowersUsers(
        @Path(UrlEndpoint.QUERY_USERNAME) username: String
    ): Call<ArrayList<Search>>

    @GET(UrlEndpoint.REPOSITORY_URL)
    fun getRepository(
        @Path(UrlEndpoint.QUERY_USERNAME) username: String
    ): Call<ArrayList<RepositoryResponse>>
}