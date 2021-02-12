package com.taufik.gitser.data.viewmodel.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.taufik.gitser.data.db.Favorite
import com.taufik.gitser.data.db.FavoriteDao
import com.taufik.gitser.data.db.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<Favorite>>? {
        return userDao?.getFavoriteUser()
    }
}