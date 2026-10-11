package com.example.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.prayer.CalculationMethod
import com.example.data.prayer.City
import com.example.data.prayer.CityData
import com.example.data.prayer.JuristicMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

data class UserSettings(
    val cityId: String = "dhaka",
    val cityName: String = "ঢাকা",
    val latitude: Double = 23.8103,
    val longitude: Double = 90.4125,
    val timeZoneId: String = "Asia/Dhaka",
    val calculationMethod: CalculationMethod = CalculationMethod.KARACHI,
    val juristicMethod: JuristicMethod = JuristicMethod.HANAFI,
    val themeMode: String = "SYSTEM", // "SYSTEM", "LIGHT", "DARK"
    val notificationsEnabled: Boolean = true,
    val fajrNotification: Boolean = true,
    val dhuhrNotification: Boolean = true,
    val asrNotification: Boolean = true,
    val maghribNotification: Boolean = true,
    val ishaNotification: Boolean = true,
    val reminderOffsetMinutes: Int = 0,
    val vibrationEnabled: Boolean = true,
    val tasbihVibration: Boolean = true,
    val tasbihSound: Boolean = true,
    val azanAlarmEnabled: Boolean = true
)

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val CITY_ID = stringPreferencesKey("city_id")
        val CITY_NAME = stringPreferencesKey("city_name")
        val LATITUDE = doublePreferencesKey("latitude")
        val LONGITUDE = doublePreferencesKey("longitude")
        val TIME_ZONE_ID = stringPreferencesKey("time_zone_id")
        val CALCULATION_METHOD = stringPreferencesKey("calculation_method")
        val JURISTIC_METHOD = stringPreferencesKey("juristic_method")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val FAJR_NOTIF = booleanPreferencesKey("fajr_notif")
        val DHUHR_NOTIF = booleanPreferencesKey("dhuhr_notif")
        val ASR_NOTIF = booleanPreferencesKey("asr_notif")
        val MAGHRIB_NOTIF = booleanPreferencesKey("maghrib_notif")
        val ISHA_NOTIF = booleanPreferencesKey("isha_notif")
        val REMINDER_OFFSET = intPreferencesKey("reminder_offset")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val TASBIH_VIBRATION = booleanPreferencesKey("tasbih_vibration")
        val TASBIH_SOUND = booleanPreferencesKey("tasbih_sound")
        val AZAN_ALARM_ENABLED = booleanPreferencesKey("azan_alarm_enabled")
    }

    val userSettingsFlow: Flow<UserSettings> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val calcMethodStr = preferences[PreferencesKeys.CALCULATION_METHOD] ?: CalculationMethod.KARACHI.name
            val calcMethod = try {
                CalculationMethod.valueOf(calcMethodStr)
            } catch (e: Exception) {
                CalculationMethod.KARACHI
            }

            val juristicStr = preferences[PreferencesKeys.JURISTIC_METHOD] ?: JuristicMethod.HANAFI.name
            val juristic = try {
                JuristicMethod.valueOf(juristicStr)
            } catch (e: Exception) {
                JuristicMethod.HANAFI
            }

            UserSettings(
                cityId = preferences[PreferencesKeys.CITY_ID] ?: CityData.DEFAULT_CITY.id,
                cityName = preferences[PreferencesKeys.CITY_NAME] ?: CityData.DEFAULT_CITY.nameBangla,
                latitude = preferences[PreferencesKeys.LATITUDE] ?: CityData.DEFAULT_CITY.latitude,
                longitude = preferences[PreferencesKeys.LONGITUDE] ?: CityData.DEFAULT_CITY.longitude,
                timeZoneId = preferences[PreferencesKeys.TIME_ZONE_ID] ?: CityData.DEFAULT_CITY.timeZoneId,
                calculationMethod = calcMethod,
                juristicMethod = juristic,
                themeMode = preferences[PreferencesKeys.THEME_MODE] ?: "SYSTEM",
                notificationsEnabled = preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: true,
                fajrNotification = preferences[PreferencesKeys.FAJR_NOTIF] ?: true,
                dhuhrNotification = preferences[PreferencesKeys.DHUHR_NOTIF] ?: true,
                asrNotification = preferences[PreferencesKeys.ASR_NOTIF] ?: true,
                maghribNotification = preferences[PreferencesKeys.MAGHRIB_NOTIF] ?: true,
                ishaNotification = preferences[PreferencesKeys.ISHA_NOTIF] ?: true,
                reminderOffsetMinutes = preferences[PreferencesKeys.REMINDER_OFFSET] ?: 0,
                vibrationEnabled = preferences[PreferencesKeys.VIBRATION_ENABLED] ?: true,
                tasbihVibration = preferences[PreferencesKeys.TASBIH_VIBRATION] ?: true,
                tasbihSound = preferences[PreferencesKeys.TASBIH_SOUND] ?: true,
                azanAlarmEnabled = preferences[PreferencesKeys.AZAN_ALARM_ENABLED] ?: true
            )
        }

    suspend fun setCity(city: City) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.CITY_ID] = city.id
            prefs[PreferencesKeys.CITY_NAME] = city.nameBangla
            prefs[PreferencesKeys.LATITUDE] = city.latitude
            prefs[PreferencesKeys.LONGITUDE] = city.longitude
            prefs[PreferencesKeys.TIME_ZONE_ID] = city.timeZoneId
        }
    }

    suspend fun setCustomLocation(name: String, latitude: Double, longitude: Double, timeZoneId: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.CITY_ID] = "custom_${System.currentTimeMillis()}"
            prefs[PreferencesKeys.CITY_NAME] = name
            prefs[PreferencesKeys.LATITUDE] = latitude
            prefs[PreferencesKeys.LONGITUDE] = longitude
            prefs[PreferencesKeys.TIME_ZONE_ID] = timeZoneId
        }
    }

    suspend fun setCalculationMethod(method: CalculationMethod) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.CALCULATION_METHOD] = method.name
        }
    }

    suspend fun setJuristicMethod(method: JuristicMethod) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.JURISTIC_METHOD] = method.name
        }
    }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.THEME_MODE] = mode
        }
    }

    suspend fun toggleNotification(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun togglePrayerNotification(prayerKey: String, enabled: Boolean) {
        context.dataStore.edit { prefs ->
            when (prayerKey.lowercase()) {
                "fajr" -> prefs[PreferencesKeys.FAJR_NOTIF] = enabled
                "dhuhr" -> prefs[PreferencesKeys.DHUHR_NOTIF] = enabled
                "asr" -> prefs[PreferencesKeys.ASR_NOTIF] = enabled
                "maghrib" -> prefs[PreferencesKeys.MAGHRIB_NOTIF] = enabled
                "isha" -> prefs[PreferencesKeys.ISHA_NOTIF] = enabled
            }
        }
    }

    suspend fun setReminderOffset(minutes: Int) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.REMINDER_OFFSET] = minutes
        }
    }

    suspend fun toggleVibration(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.VIBRATION_ENABLED] = enabled
        }
    }

    suspend fun toggleTasbihVibration(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.TASBIH_VIBRATION] = enabled
        }
    }

    suspend fun toggleTasbihSound(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.TASBIH_SOUND] = enabled
        }
    }

    suspend fun toggleAzanAlarm(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.AZAN_ALARM_ENABLED] = enabled
        }
    }
}
