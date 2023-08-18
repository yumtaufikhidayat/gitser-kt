package com.taufik.gitser.data.viewmodel.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.response.search.Search
import com.taufik.gitser.data.response.search.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val apiConfig = ApiClient.apiInstance

    private val _listUsers = MutableLiveData<ArrayList<Search>>()
    val listUsers: LiveData<ArrayList<Search>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearchUsers(query: String) {
        _isLoading.value = true
        apiConfig
            .searchUsers(query)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        _listUsers.postValue(response.body()?.items)
                        Log.e("listUsers", "onResponse: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("errorRetrofit", "onFailure: ${t.localizedMessage}")
                }
            })

    }
}