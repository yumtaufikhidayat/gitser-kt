package com.taufik.gitser.data.viewmodel.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.response.detail.RepositoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel : ViewModel() {

    private val apiConfig = ApiClient.apiInstance

    private val _listOfRepository = MutableLiveData<ArrayList<RepositoryResponse>>()
    val listOfRepository: LiveData<ArrayList<RepositoryResponse>> = _listOfRepository

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setListOfRepository(username: String) {
        _isLoading.value = true
        apiConfig.getRepository(username)
            .enqueue(object : Callback<ArrayList<RepositoryResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<RepositoryResponse>>,
                    response: Response<ArrayList<RepositoryResponse>>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.value = false
                       _listOfRepository.postValue(response.body())
                    }

                    Log.e("repoSuccess", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<ArrayList<RepositoryResponse>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("repoFailed", "onFailure: ${t.localizedMessage}")
                }
            })
    }
}