package com.taufik.gitser.ui.activity.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.taufik.gitser.data.response.settings.Reminder
import com.taufik.gitser.databinding.ActivitySettingsBinding
import com.taufik.gitser.preferences.ReminderPreferences
import com.taufik.gitser.receiver.AlarmReceiver
import com.taufik.gitser.utils.applyEdgeToEdgeInsets

class SettingsActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        applyEdgeToEdgeInsets(window, binding.root)

        initActionBar()
        setReminderPreferences()
        setAlarmReceiver()
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            title = "Pengaturan"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setReminderPreferences() {
        val reminderPreferences = ReminderPreferences(this)
        binding.swAlarm.isChecked = reminderPreferences.getReminder().isReminded
    }

    private fun setAlarmReceiver() {
        alarmReceiver = AlarmReceiver()
        binding.swAlarm.setOnCheckedChangeListener{_, isChecked ->
            if (isChecked) {
                saveReminder(true)
                alarmReceiver.repeatAlarm(this, "09:00")
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(isSave: Boolean) {
        val reminderPreferences = ReminderPreferences(this)
        reminder = Reminder()
        reminder.isReminded = isSave
        reminderPreferences.setReminder(reminder)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}