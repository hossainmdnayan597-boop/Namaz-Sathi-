package com.example.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity
import com.example.R
import com.example.data.prayer.DayPrayerSchedule
import com.example.data.prayer.PrayerType
import com.example.data.preferences.UserSettings

object PrayerNotificationHelper {

    const val CHANNEL_ID = "namaz_sathi_prayer_channel"
    private const val CHANNEL_NAME = "নামাজের ওয়াক্ত নোটিফিকেশন"
    private const val CHANNEL_DESCRIPTION = "প্রতি ওয়াক্ত নামাজের সময়ের সুমধুর স্মরণিকা"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
                setShowBadge(true)
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun showPrayerNotification(context: Context, prayerType: PrayerType, formattedTime: String) {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            prayerType.ordinal,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
        )

        val duaText = when (prayerType) {
            PrayerType.FAJR -> "الصَّلَاةُ خَيْرٌ مِنَ النَّوْمِ — ঘুমের চেয়ে নামাজ উত্তম।"
            PrayerType.DHUHR -> "حي على الصلاة — আসুন নামাজের দিকে, মহান রবের স্মরণে।"
            PrayerType.ASR -> "নামাজের প্রতি যত্নবান হোন, বিশেষ করে মধ্যবর্তী আসরের সালাতের।"
            PrayerType.MAGHRIB -> "মাগরিবের সময় হয়েছে। ইফতার ও সালাতের অপূর্ব মুহূর্ত।"
            PrayerType.ISHA -> "এশার সালাতের ওয়াক্ত হয়েছে। দিনের শেষ ইবাদতে মগ্ন হোন।"
            else -> "সালাত মুমিনের মেরাজ — নামাজের ওয়াক্ত হয়েছে।"
        }

        val stopIntent = Intent(context, StopAzanReceiver::class.java).apply {
            putExtra("NOTIFICATION_ID", prayerType.ordinal + 100)
        }
        val stopPendingIntent = PendingIntent.getBroadcast(
            context,
            prayerType.ordinal + 500,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("🕌 ${prayerType.banglaName} নামাজের সময় হয়েছে (${formattedTime})")
            .setContentText(duaText)
            .setStyle(NotificationCompat.BigTextStyle().bigText("🕌 ${prayerType.banglaName} নামাজের ওয়াক্ত শুরু হয়েছে (${formattedTime})\n\n$duaText\n\nরাকাত: ${prayerType.rakatsSummary}"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(android.R.drawable.ic_media_pause, "আযান থামান", stopPendingIntent)

        try {
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(prayerType.ordinal + 100, builder.build())
        } catch (e: SecurityException) {
            // Permission denied
        }
    }

    fun scheduleAlarmsForSchedule(
        context: Context,
        schedule: DayPrayerSchedule,
        settings: UserSettings
    ) {
        if (!settings.notificationsEnabled) {
            cancelAllAlarms(context)
            return
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val offsetMillis = settings.reminderOffsetMinutes * 60 * 1000L
        val now = System.currentTimeMillis()

        val prayersToSchedule = listOf(
            Pair(schedule.fajr, settings.fajrNotification),
            Pair(schedule.dhuhr, settings.dhuhrNotification),
            Pair(schedule.asr, settings.asrNotification),
            Pair(schedule.maghrib, settings.maghribNotification),
            Pair(schedule.isha, settings.ishaNotification)
        )

        for ((item, enabled) in prayersToSchedule) {
            if (!enabled) {
                cancelAlarmForPrayer(context, item.type)
                continue
            }

            val triggerTime = item.timeMillis - offsetMillis
            if (triggerTime > now) {
                val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
                    putExtra("PRAYER_TYPE", item.type.name)
                    putExtra("PRAYER_TIME", item.formattedBanglaTime)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    item.type.ordinal,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
                )

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                    } else {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                    }
                } catch (e: SecurityException) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                }
            }
        }
    }

    fun cancelAlarmForPrayer(context: Context, prayerType: PrayerType) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, PrayerAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            prayerType.ordinal,
            intent,
            PendingIntent.FLAG_NO_CREATE or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
        )
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }
    }

    fun cancelAllAlarms(context: Context) {
        for (type in PrayerType.values()) {
            cancelAlarmForPrayer(context, type)
        }
    }
}
