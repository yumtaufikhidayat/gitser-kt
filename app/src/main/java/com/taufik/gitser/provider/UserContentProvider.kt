package com.taufik.gitser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.taufik.gitser.data.db.FavoriteDao
import com.taufik.gitser.data.db.UserDatabase

class UserContentProvider : ContentProvider() {

    companion object{
        private const val AUTHORITY = "com.taufik.gitser"
        private const val TABLE_NAME = "favorite_user"
        const val FAVORITE_USER_DATA_ID = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_USER_DATA_ID)
        }
    }

    private lateinit var userDao: FavoriteDao

    override fun onCreate(): Boolean {
        userDao = context?.let {
            UserDatabase.getDatabase(it)?.favoriteUserDao()
        }!!

        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            FAVORITE_USER_DATA_ID -> {
                cursor = userDao.findAllUser()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }

            else -> cursor = null
        }

        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}