package com.taufik.gitser.api

import com.taufik.gitser.BuildConfig
import com.taufik.gitser.data.model.UserResponse
import com.taufik.gitser.data.utils.Utils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    @GET(Utils.SEARCH_USERS)
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN_PAT}")
    fun searchUsers(
            @Query("q") query: String
    ): Call<UserResponse>
}