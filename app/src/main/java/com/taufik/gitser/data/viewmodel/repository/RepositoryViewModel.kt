package com.taufik.gitser.data.viewmodel.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.model.repository.RepositoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel : ViewModel() {

    private val listOfRepository = MutableLiveData<ArrayList<RepositoryResponse>>()

    fun setListOfRepository(username: String) {
        ApiClient.apiInstance
            .getRepository(username)
            .enqueue(object : Callback<ArrayList<RepositoryResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<RepositoryResponse>>,
                    response: Response<ArrayList<RepositoryResponse>>
                ) {
                    if (response.isSuccessful) {
                        listOfRepository.postValue(response.body())
                    }

                    Log.e("repoSuccess", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<ArrayList<RepositoryResponse>>, t: Throwable) {
                    Log.e("repoFailed", "onFailure: ${t.localizedMessage}")
                }
            })
    }

    fun getListOfRepository(): LiveData<ArrayList<RepositoryResponse>> {
        return listOfRepository
    }
}