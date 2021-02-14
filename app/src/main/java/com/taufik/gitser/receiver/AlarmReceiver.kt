package com.taufik.gitser.receiver

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.taufik.gitser.R
import com.taufik.gitser.ui.activity.search.SearchActivity
import es.dmoral.toasty.Toasty
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object{
        private const val CHANNEL_ID = "channel_1"
        private const val CHANNEL_NAME = "Gitser Reminder"
        private const val TIME_FORMAT = "HH:mm"
        private const val EXTRA_TITLE = "com.taufik.gitser.receiver.EXTRA_TITLE"
        private const val EXTRA_MESSAGE = "com.taufik.gitser.receiver.EXTRA_MESSAGE"
        private const val TITLE_REMINDER = "Reminder"
        private const val TITLE_MESSAGE = "Jangan lupa cari user favorit Github Anda hari ini"
        private const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        if (message != null) {
            sendNotification(context, message)
        }
    }

    private fun sendNotification(context: Context, message: String) {

        val requestId = System.currentTimeMillis().toInt()
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mIntent = Intent(context, SearchActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(context, requestId, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(TITLE_REMINDER)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setSound(ringtoneManager)
            .setChannelId(CHANNEL_ID)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.gitser_kt_logo))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.enableVibration(true)
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager.notify(ID_REPEATING, notification)
    }

    fun repeatAlarm(
        context: Context,
        time: String
    ) {

        if (isDateInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_TITLE, TITLE_REMINDER)
        intent.putExtra(EXTRA_MESSAGE, TITLE_MESSAGE)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)

        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toasty.success(context, "Alarm pengingat telah diatur", Toast.LENGTH_SHORT, true).show()
    }

    fun cancelAlarm(context: Context) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)

        Toasty.success(context, "Alarm pengingat dibatalkan", Toast.LENGTH_SHORT, true).show()
    }

    private fun isDateInvalid(time: String, format: String): Boolean{

        return try {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            dateFormat.isLenient = false
            dateFormat.parse(time)
            false
        } catch (e: ParseException) {
            Log.e("isDateError", "isDateInvalid: ${e.localizedMessage}")
            true
        }
    }
}