package com.taufik.consumerapp

import android.database.Cursor

object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<Search>{
        val list = ArrayList<Search>()
        if (cursor != null) {
            while (cursor.moveToNext()) {

                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.ID))
                val username =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.USERNAME))
                val avatarUrl =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.AVATAR_URL))

                list.add(Search(id, username, avatarUrl))
            }
        }

        return list
    }
}