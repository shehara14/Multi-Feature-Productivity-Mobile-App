package com.example.tasklytic

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationTitle = intent.getStringExtra(titleExtra) ?: "No Title"
        val notificationMessage = intent.getStringExtra(messageExtra) ?: "No Message"

        // Create an intent for when the notification is dismissed
        val deleteIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, NotificationDismissedReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setDeleteIntent(deleteIntent) // Set delete intent
            .build()

        // Notify the user
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}
