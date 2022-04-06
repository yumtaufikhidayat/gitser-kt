package com.taufik.gitser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteEntity(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String
): Serializable
