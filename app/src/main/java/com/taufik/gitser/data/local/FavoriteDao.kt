package com.taufik.gitser.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addUserToFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteEntity>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUserFavorite(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeUserFromFavorite(id: Int): Int

    @Query("SELECT * FROM favorite_user")
    fun findAllUser(): Cursor
}