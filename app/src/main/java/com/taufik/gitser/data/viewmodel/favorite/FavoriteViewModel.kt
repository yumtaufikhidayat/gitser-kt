package com.taufik.gitser.data.viewmodel.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.taufik.gitser.data.local.FavoriteEntity
import com.taufik.gitser.data.local.FavoriteDao
import com.taufik.gitser.data.local.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteEntity>>? {
        return userDao?.getFavoriteUser()
    }
}