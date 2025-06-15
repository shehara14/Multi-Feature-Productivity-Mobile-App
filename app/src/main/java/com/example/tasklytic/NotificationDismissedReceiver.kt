package com.example.tasklytic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationDismissedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Clear widget data when the notification is dismissed
        TaskWidgetProvider.clearWidget(context)
    }
}
