package com.example.damanhacker.utlities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.damanhacker.MainActivity
import com.example.damanhacker.R

object NotificationUtils {

    // Notification ID.
    private const val NOTIFICATION_ID = 0

    fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {


        // TODO: Step 1.11 create intent
        val contentIntent = Intent(applicationContext, MainActivity::class.java)

        // TODO: Step 1.12 create PendingIntent
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // TODO: You can add style here


        // TODO: Step 1.2 get an instance of NotificationCompat.Builder

        // Build the notification
        val builder = NotificationCompat.Builder(
            applicationContext,
            // TODO: Step 1.8 use a notification channel
            applicationContext.getString(R.string.app_name)
        )
            // TODO: Step 1.3 set title, text and icon to builder
            .setSmallIcon(R.drawable.ic_menu_gallery)
            .setContentTitle(applicationContext.getString(R.string.nav_header_title))
            .setContentText(messageBody)
            // TODO: Step 1.13 set content intent
            .setContentIntent(contentPendingIntent)

            // TODO: Step 2.5 set priority
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // TODO Step 1.4 call notify
        // Deliver the notification
        notify(NOTIFICATION_ID, builder.build())
    }

// TODO: Step 1.14 Cancel all notifications
    /**
     * Cancels all notifications.
     *
     */
    fun NotificationManager.cancelNotifications() {
        cancelAll()
    }

}