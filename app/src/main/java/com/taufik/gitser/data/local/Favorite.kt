package com.taufik.gitser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class Favorite(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String
): Serializable
