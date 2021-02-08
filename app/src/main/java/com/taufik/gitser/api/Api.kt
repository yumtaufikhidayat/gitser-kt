package com.taufik.gitser.api

import com.taufik.gitser.data.model.UserResponse
import com.taufik.gitser.data.utils.Utils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    @GET(Utils.SEARCH_USERS)
    @Headers("Authorization: token cee369e60ed3e1c908d333ace65ff1fcfb9d46d6")
    fun searchUsers(
            @Query("q") query: String
    ): Call<UserResponse>
}