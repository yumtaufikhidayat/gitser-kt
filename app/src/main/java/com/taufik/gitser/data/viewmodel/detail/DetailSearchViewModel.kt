package com.taufik.gitser.data.viewmodel.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.model.detail.DetailSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSearchViewModel : ViewModel() {

    val user = MutableLiveData<DetailSearchResponse>()

    fun setDetailSearch(username: String) {
        ApiClient.apiInstance.getDetailUsers(username)
            .enqueue(object : Callback<DetailSearchResponse> {
                override fun onResponse(
                    call: Call<DetailSearchResponse>,
                    response: Response<DetailSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }

                    Log.e("detailSuccess", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<DetailSearchResponse>, t: Throwable) {
                    Log.e("detailFailed", "onFailure: ${t.localizedMessage}")
                }

            })
    }

    fun getDetailSearch(): LiveData<DetailSearchResponse> {
        return user
    }
}