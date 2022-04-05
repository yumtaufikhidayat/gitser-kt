package com.taufik.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    private const val AUTHORITY = "com.taufik.gitser"
    private const val SCHEME = "content"

    class FavoriteUserColumn : BaseColumns {
        companion object{
            private const val TABLE_NAME = "favorite_user"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR_URL = "avatarUrl"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}