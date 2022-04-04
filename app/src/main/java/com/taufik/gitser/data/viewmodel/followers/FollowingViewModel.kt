package com.taufik.gitser.data.viewmodel.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.response.search.Search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    val listOfFollowing = MutableLiveData<ArrayList<Search>>()

    fun setListOfFollowing(username: String) {
        ApiClient.apiInstance
            .getFollowingUsers(username)
            .enqueue(object : Callback<ArrayList<Search>> {
                override fun onResponse(
                    call: Call<ArrayList<Search>>,
                    response: Response<ArrayList<Search>>
                ) {
                    if (response.isSuccessful) {
                        listOfFollowing.postValue(response.body())
                    }

                    Log.e("followingSuccess", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<ArrayList<Search>>, t: Throwable) {
                    Log.e("followingFailed", "onFailure: ${t.localizedMessage}")
                }
            })
    }

    fun getListOfFollowing(): LiveData<ArrayList<Search>> {
        return listOfFollowing
    }
}