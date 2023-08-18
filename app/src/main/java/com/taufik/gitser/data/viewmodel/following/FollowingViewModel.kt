package com.taufik.gitser.data.viewmodel.following

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

    private val apiConfig = ApiClient.apiInstance

    private val _listOfFollowing = MutableLiveData<ArrayList<Search>>()
    val listOfFollowing: LiveData<ArrayList<Search>> = _listOfFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setListOfFollowing(username: String) {
        _isLoading.value = true
        apiConfig.getFollowingUsers(username)
            .enqueue(object : Callback<ArrayList<Search>> {
                override fun onResponse(
                    call: Call<ArrayList<Search>>,
                    response: Response<ArrayList<Search>>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        _listOfFollowing.postValue(response.body())
                    }

                    Log.e("followingSuccess", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<ArrayList<Search>>, t: Throwable) {
                    _isLoading.value = true
                    Log.e("followingFailed", "onFailure: ${t.localizedMessage}")
                }
            })
    }
}