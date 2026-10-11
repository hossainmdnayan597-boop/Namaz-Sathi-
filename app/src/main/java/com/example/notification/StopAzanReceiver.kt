package com.example.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class StopAzanReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        AzanAlarmSoundManager.stopAzan()

        val notificationId = intent?.getIntExtra("NOTIFICATION_ID", -1) ?: -1
        if (notificationId != -1) {
            try {
                NotificationManagerCompat.from(context).cancel(notificationId)
            } catch (_: Exception) {}
        }
    }
}
