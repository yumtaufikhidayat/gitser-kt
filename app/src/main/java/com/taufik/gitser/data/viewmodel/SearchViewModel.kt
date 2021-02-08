package com.taufik.gitser.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.model.User
import com.taufik.gitser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String) {
        ApiClient.apiInstance
                .searchUsers(query)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if (response.isSuccessful) {
                            listUsers.postValue(response.body()?.items)
                            Log.e("listUsers", "onResponse: ${response.body()}" )
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.e("errorRetrofit", "onFailure: ${t.localizedMessage}")
                    }

                })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}