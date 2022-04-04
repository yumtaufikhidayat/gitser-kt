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

    val listUsers = MutableLiveData<ArrayList<Search>>()

    fun setSearchUsers(query: String) {
        ApiClient.apiInstance
                .searchUsers(query)
                .enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                        if (response.isSuccessful) {
                            listUsers.postValue(response.body()?.items)
                            Log.e("listUsers", "onResponse: ${response.body()}" )
                        }
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Log.e("errorRetrofit", "onFailure: ${t.localizedMessage}")
                    }
                })
    }

    fun getSearchUsers(): LiveData<ArrayList<Search>> {
        return listUsers
    }
}