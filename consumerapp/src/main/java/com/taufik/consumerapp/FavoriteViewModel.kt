package com.taufik.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var listOfUsers = MutableLiveData<ArrayList<Search>>()

    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUserColumn.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val cursorToArrayList = MappingHelper.mapCursorToArrayList(cursor)
        listOfUsers.postValue(cursorToArrayList)
    }

    fun getFavoriteUser(): LiveData<ArrayList<Search>> {
        return listOfUsers
    }
}