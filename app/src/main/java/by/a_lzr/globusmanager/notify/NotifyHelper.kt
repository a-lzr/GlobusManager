package by.a_lzr.globusmanager.notify

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import by.a_lzr.globusmanager.R

const val NOTIFY_CHANNEL_ID = "NOTIFY_CHANNEL_ID"

object NotifyHelper {

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFY_CHANNEL_ID,
                "ServiceChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager =
                getSystemService(context, NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun buildNotify(context: Context, title: String): Notification {
        return NotificationCompat.Builder(context, NOTIFY_CHANNEL_ID)
            .setContentTitle(title)
//            .setContentText("text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }
}