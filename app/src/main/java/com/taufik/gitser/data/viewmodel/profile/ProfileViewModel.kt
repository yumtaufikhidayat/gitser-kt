package com.taufik.gitser.data.viewmodel.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.response.detail.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    val user = MutableLiveData<DetailResponse>()

    fun setProfile(username: String) {
        ApiClient.apiInstance.getDetailUsers(username)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }

                    Log.e("detailSuccess", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.e("detailFailed", "onFailure: ${t.localizedMessage}")
                }

            })
    }

    fun getProfile(): LiveData<DetailResponse> {
        return user
    }
}