package com.taufik.gitser.ui.activity.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.taufik.gitser.data.model.settings.Reminder
import com.taufik.gitser.databinding.ActivitySettingsBinding
import com.taufik.gitser.preferences.ReminderPreferences
import com.taufik.gitser.receiver.AlarmReceiver

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun saveReminder(state: Boolean) {

        val reminderPreferences = ReminderPreferences(this)
        reminder = Reminder()
        reminder.isReminded = state
        reminderPreferences.setReminder(reminder)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}