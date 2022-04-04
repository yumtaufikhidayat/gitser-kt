package com.taufik.gitser.data.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.response.search.Search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listAllUsers = MutableLiveData<ArrayList<Search>>()

    fun setAllUsers() {
        ApiClient
            .apiInstance
            .getAllUsers()
            .enqueue(object : Callback<ArrayList<Search>> {
                override fun onResponse(
                    call: Call<ArrayList<Search>>,
                    response: Response<ArrayList<Search>>
                ) {
                    if (response.isSuccessful) {
                        listAllUsers.postValue(response.body())
                    }

                    Log.e("mainSuccess", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<ArrayList<Search>>, t: Throwable) {
                    Log.e("mainFailed", "onFailure: ${t.localizedMessage}")
                }

            })
    }

    fun getAllUsers(): LiveData<ArrayList<Search>>{
        return listAllUsers
    }
}