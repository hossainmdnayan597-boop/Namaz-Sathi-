package com.example.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.data.prayer.CalculationMethod
import com.example.data.prayer.JuristicMethod
import com.example.data.prayer.PrayerCalculator
import com.example.data.prayer.PrayerType
import com.example.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

class PrayerAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val prayerTypeName = intent.getStringExtra("PRAYER_TYPE") ?: return
        val formattedTime = intent.getStringExtra("PRAYER_TIME") ?: ""

        val prayerType = try {
            PrayerType.valueOf(prayerTypeName)
        } catch (e: Exception) {
            PrayerType.FAJR
        }

        PrayerNotificationHelper.showPrayerNotification(context, prayerType, formattedTime)

        // Reschedule next prayer alarms and play Azan in background if enabled
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prefRepo = UserPreferencesRepository(context)
                val settings = prefRepo.userSettingsFlow.first()

                if (settings.azanAlarmEnabled) {
                    AzanAlarmSoundManager.playAzan(context, prayerType.banglaName)
                }

                val timeZone = java.util.TimeZone.getTimeZone(settings.timeZoneId)
                val cal = Calendar.getInstance(timeZone)
                val tzOffset = timeZone.getOffset(System.currentTimeMillis()) / 3600000.0
                val schedule = PrayerCalculator.calculatePrayerTimes(
                    year = cal.get(Calendar.YEAR),
                    month = cal.get(Calendar.MONTH) + 1,
                    day = cal.get(Calendar.DAY_OF_MONTH),
                    latitude = settings.latitude,
                    longitude = settings.longitude,
                    timeZoneOffsetHours = tzOffset,
                    timeZoneId = settings.timeZoneId,
                    method = settings.calculationMethod,
                    juristic = settings.juristicMethod
                )
                PrayerNotificationHelper.scheduleAlarmsForSchedule(context, schedule, settings)
            } finally {
                pendingResult.finish()
            }
        }
    }
}
