package com.taufik.gitser.data.viewmodel.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.response.search.Search
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val listOfFollowers = MutableLiveData<ArrayList<Search>>()

    fun setListOfFollowers(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            ApiClient.apiInstance
                .getFollowersUsers(username)
                .enqueue(object : Callback<ArrayList<Search>> {
                    override fun onResponse(
                        call: Call<ArrayList<Search>>,
                        response: Response<ArrayList<Search>>
                    ) {
                        if (response.isSuccessful) {
                            listOfFollowers.postValue(response.body())
                        }

                        Log.e("followersSuccess", "onResponse: ${response.body()}")
                    }

                    override fun onFailure(call: Call<ArrayList<Search>>, t: Throwable) {
                        Log.e("followersFailed", "onFailure: ${t.localizedMessage}")
                    }
                })
        }
    }

    fun getListOfFollowers(): LiveData<ArrayList<Search>> {
        return listOfFollowers
    }
}