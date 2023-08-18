package com.taufik.gitser.data.viewmodel.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taufik.gitser.api.ApiClient
import com.taufik.gitser.data.local.FavoriteDao
import com.taufik.gitser.data.local.FavoriteEntity
import com.taufik.gitser.data.local.UserDatabase
import com.taufik.gitser.data.response.detail.DetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val apiConfig = ApiClient.apiInstance

    private val _userDetail = MutableLiveData<DetailResponse>()
    val userDetail: LiveData<DetailResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var userDao: FavoriteDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun setDetailSearch(username: String) {
        _isLoading.value = true
        apiConfig.getDetailUsers(username)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        _userDetail.postValue(response.body())
                    }

                    Log.e("detailSuccess", "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("detailFailed", "onFailure: ${t.localizedMessage}")
                }
            })
    }

    fun addToFavorite(id: Int, username: String, avatarUrl: String, type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteEntity(
                id,
                username,
                avatarUrl,
                type
            )

            userDao?.addUserToFavorite(user)
        }
    }

    suspend fun checkUserFavorite(id: Int) = userDao?.checkUserFavorite(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeUserFromFavorite(id)
        }
    }
}