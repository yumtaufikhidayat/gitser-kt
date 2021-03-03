package com.taufik.gitser.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apiInstance: Api = retrofit.create(Api::class.java)
}