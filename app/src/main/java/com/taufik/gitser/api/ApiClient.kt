package com.taufik.gitser.api

import com.taufik.gitser.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val timeOutTime = 30L
    private val timeUnitSecs = TimeUnit.SECONDS

    private val interceptor = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else
        HttpLoggingInterceptor.Level.NONE

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(interceptor))
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("GITHUB_PAT", BuildConfig.GITHUB_TOKEN_PAT)
                .build()
            return@addInterceptor chain.proceed(request)
        }
        .connectTimeout(timeOutTime, timeUnitSecs)
        .writeTimeout(timeOutTime, timeUnitSecs)
        .readTimeout(timeOutTime, timeUnitSecs)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(UrlEndpoint.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance: Api = retrofit.create(Api::class.java)
}