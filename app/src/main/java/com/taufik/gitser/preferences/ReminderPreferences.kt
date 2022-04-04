package com.taufik.gitser.preferences

import android.content.Context
import com.taufik.gitser.data.response.settings.Reminder

class ReminderPreferences(context: Context) {

    companion object{
        const val PREFS_NAME = "reminderPreferences"
        const val REMINDER = "isReminder"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Reminder) {

        val editor = preferences.edit()
        editor.putBoolean(REMINDER, value.isReminded)
        editor.apply()
    }

    fun getReminder(): Reminder {

        val model = Reminder()
        model.isReminded = preferences.getBoolean(REMINDER, false)
        return model
    }
}