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

    private val apiConfig = ApiClient.apiInstance

    private val _listAllUsers = MutableLiveData<ArrayList<Search>>()
    val listAllUsers: LiveData<ArrayList<Search>> = _listAllUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        setAllUsers()
    }

    private fun setAllUsers() {
        _isLoading.value = true
        apiConfig.getAllUsers()
            .enqueue(object : Callback<ArrayList<Search>> {
                override fun onResponse(
                    call: Call<ArrayList<Search>>,
                    response: Response<ArrayList<Search>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listAllUsers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Search>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("mainFailed", "onFailure: ${t.localizedMessage}")
                }
            })
    }
}