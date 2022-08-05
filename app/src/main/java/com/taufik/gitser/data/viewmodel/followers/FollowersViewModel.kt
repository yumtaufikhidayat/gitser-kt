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

class FollowersViewModel : ViewModel() {

    private val apiConfig = ApiClient.apiInstance

    private val _listOfFollowers = MutableLiveData<ArrayList<Search>>()
    val listOfFollowers: LiveData<ArrayList<Search>> = _listOfFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setListOfFollowers(username: String) {
        _isLoading.value = true
        apiConfig.getFollowersUsers(username)
            .enqueue(object : Callback<ArrayList<Search>> {
                override fun onResponse(
                    call: Call<ArrayList<Search>>,
                    response: Response<ArrayList<Search>>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        _listOfFollowers.postValue(response.body())
                    }

                    Log.e("followersSuccess", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<ArrayList<Search>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("followersFailed", "onFailure: ${t.localizedMessage}")
                }
            })
    }
}