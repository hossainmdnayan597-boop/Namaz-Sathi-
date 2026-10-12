package com.example.ui

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.AppDatabase
import com.example.data.database.PrayerLog
import com.example.data.database.PrayerRepository
import com.example.data.database.TasbihItem
import com.example.data.tasbih.TasbihRecord
import com.example.data.tasbih.TasbihRecordRepository
import com.example.data.prayer.*
import com.example.data.preferences.UserPreferencesRepository
import com.example.data.preferences.UserSettings
import com.example.data.qibla.CompassSensorManager
import com.example.data.qibla.QiblaCalculator
import com.example.notification.AzanAlarmSoundManager
import com.example.notification.PrayerNotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application, viewModelScope)
    val prayerRepository = PrayerRepository(db.prayerDao())
    val preferencesRepository = UserPreferencesRepository(application)
    val tasbihRecordRepository = TasbihRecordRepository(application)
    private val compassSensorManager = CompassSensorManager(application)

    // User settings
    val userSettings: StateFlow<UserSettings> = preferencesRepository.userSettingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UserSettings()
        )

    // Current time ticker (updates every second for live countdowns)
    private val _currentTimeMillis = MutableStateFlow(System.currentTimeMillis())
    val currentTimeMillis: StateFlow<Long> = _currentTimeMillis.asStateFlow()

    // Selected screen tab: 0=Home (সময়), 1=Quran (কুরআন), 2=Hadith (হাদিস), 3=Learning (শিক্ষা), 4=Tools (টুলস)
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Tools sub-tab: 0=Qibla, 1=Tasbih, 2=Tracker
    private val _toolsSubTab = MutableStateFlow(0)
    val toolsSubTab: StateFlow<Int> = _toolsSubTab.asStateFlow()

    fun setToolsSubTab(subTab: Int) {
        _toolsSubTab.value = subTab
    }

    fun navigateToTool(subTab: Int) {
        _toolsSubTab.value = subTab
        _selectedTab.value = 4
    }

    // Sub-screens & Dialogs
    private val _showSettings = MutableStateFlow(false)
    val showSettings: StateFlow<Boolean> = _showSettings.asStateFlow()

    private val _showCityPicker = MutableStateFlow(false)
    val showCityPicker: StateFlow<Boolean> = _showCityPicker.asStateFlow()

    private val _showCustomLocationDialog = MutableStateFlow(false)
    val showCustomLocationDialog: StateFlow<Boolean> = _showCustomLocationDialog.asStateFlow()

    // Active date string for prayer view/tracker
    private val _selectedDateString = MutableStateFlow(prayerRepository.getTodayDateString("Asia/Dhaka"))
    val selectedDateString: StateFlow<String> = _selectedDateString.asStateFlow()

    // Day schedule flow (reacts to date and user settings)
    val daySchedule: StateFlow<DayPrayerSchedule> = combine(
        userSettings,
        selectedDateString
    ) { settings, dateStr ->
        val timeZone = java.util.TimeZone.getTimeZone(settings.timeZoneId)
        val parts = dateStr.split("-")
        val cal = Calendar.getInstance(timeZone)
        val year = parts.getOrNull(0)?.toIntOrNull() ?: cal.get(Calendar.YEAR)
        val month = parts.getOrNull(1)?.toIntOrNull() ?: (cal.get(Calendar.MONTH) + 1)
        val day = parts.getOrNull(2)?.toIntOrNull() ?: cal.get(Calendar.DAY_OF_MONTH)

        val tzOffset = timeZone.getOffset(System.currentTimeMillis()) / 3600000.0

        val schedule = PrayerCalculator.calculatePrayerTimes(
            year = year,
            month = month,
            day = day,
            latitude = settings.latitude,
            longitude = settings.longitude,
            timeZoneOffsetHours = tzOffset,
            timeZoneId = settings.timeZoneId,
            method = settings.calculationMethod,
            juristic = settings.juristicMethod
        )

        // Reschedule alarms if notifications enabled
        if (settings.notificationsEnabled && dateStr == prayerRepository.getTodayDateString(settings.timeZoneId)) {
            PrayerNotificationHelper.scheduleAlarmsForSchedule(getApplication(), schedule, settings)
        }

        schedule
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = run {
            val tz = java.util.TimeZone.getTimeZone("Asia/Dhaka")
            val cal = Calendar.getInstance(tz)
            PrayerCalculator.calculatePrayerTimes(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH),
                CityData.DEFAULT_CITY.latitude,
                CityData.DEFAULT_CITY.longitude,
                timeZoneOffsetHours = 6.0,
                timeZoneId = "Asia/Dhaka"
            )
        }
    )

    // Current prayer status
    val currentPrayerStatus: StateFlow<CurrentPrayerStatus> = combine(
        currentTimeMillis,
        daySchedule
    ) { now, schedule ->
        PrayerCalculator.getCurrentPrayerStatus(now, schedule)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PrayerCalculator.getCurrentPrayerStatus(System.currentTimeMillis(), daySchedule.value)
    )

    // Today's prayer log from Room
    val todayPrayerLog: StateFlow<PrayerLog?> = _selectedDateString.flatMapLatest { date ->
        prayerRepository.getLogForDate(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // Recent 60 days logs for streak & statistics
    val recentPrayerLogs: StateFlow<List<PrayerLog>> = prayerRepository.getRecentLogs()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Tasbih items
    val tasbihItems: StateFlow<List<TasbihItem>> = prayerRepository.getAllTasbihItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedTasbihIndex = MutableStateFlow(0)
    val selectedTasbihIndex: StateFlow<Int> = _selectedTasbihIndex.asStateFlow()

    // Saved Tasbih records log
    val savedTasbihRecords: StateFlow<List<TasbihRecord>> = tasbihRecordRepository.records

    // Compass
    val isCompassAvailable: Boolean = compassSensorManager.isSensorAvailable

    val compassHeading: StateFlow<Float> = compassSensorManager.getDeviceAzimuth()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0f
        )

    val qiblaBearing: StateFlow<Double> = userSettings.map { settings ->
        QiblaCalculator.calculateQiblaBearing(settings.latitude, settings.longitude)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = QiblaCalculator.calculateQiblaBearing(CityData.DEFAULT_CITY.latitude, CityData.DEFAULT_CITY.longitude)
    )

    val distanceToKaabaKm: StateFlow<Double> = userSettings.map { settings ->
        QiblaCalculator.calculateDistanceToKaaba(settings.latitude, settings.longitude)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = QiblaCalculator.calculateDistanceToKaaba(CityData.DEFAULT_CITY.latitude, CityData.DEFAULT_CITY.longitude)
    )

    init {
        // Ticker loop
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                _currentTimeMillis.value = System.currentTimeMillis()
                delay(1000L)
            }
        }

        // Seed default tasbihs
        viewModelScope.launch(Dispatchers.IO) {
            prayerRepository.ensureDefaultTasbihsIfEmpty()
        }
    }

    fun setSelectedTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
        if (_showSettings.value) _showSettings.value = false
    }

    fun openSettings(show: Boolean) {
        _showSettings.value = show
    }

    fun openCityPicker(show: Boolean) {
        _showCityPicker.value = show
    }

    fun openCustomLocationDialog(show: Boolean) {
        _showCustomLocationDialog.value = show
    }

    fun selectCity(city: City) {
        viewModelScope.launch {
            preferencesRepository.setCity(city)
            _selectedDateString.value = prayerRepository.getTodayDateString(city.timeZoneId)
            _showCityPicker.value = false
        }
    }

    fun saveCustomLocation(name: String, lat: Double, lon: Double) {
        viewModelScope.launch {
            preferencesRepository.setCustomLocation(name, lat, lon, "Asia/Dhaka")
            _selectedDateString.value = prayerRepository.getTodayDateString("Asia/Dhaka")
            _showCustomLocationDialog.value = false
        }
    }

    fun setCalculationMethod(method: CalculationMethod) {
        viewModelScope.launch {
            preferencesRepository.setCalculationMethod(method)
        }
    }

    fun setJuristicMethod(juristic: JuristicMethod) {
        viewModelScope.launch {
            preferencesRepository.setJuristicMethod(juristic)
        }
    }

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            preferencesRepository.setThemeMode(mode)
        }
    }

    fun toggleMasterNotification(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.toggleNotification(enabled)
            if (!enabled) {
                PrayerNotificationHelper.cancelAllAlarms(getApplication())
            }
        }
    }

    fun togglePrayerNotification(prayerName: String, enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.togglePrayerNotification(prayerName, enabled)
        }
    }

    fun setReminderOffset(minutes: Int) {
        viewModelScope.launch {
            preferencesRepository.setReminderOffset(minutes)
        }
    }

    fun toggleTasbihVibration(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.toggleTasbihVibration(enabled)
        }
    }

    fun toggleTasbihSound(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.toggleTasbihSound(enabled)
        }
    }

    fun toggleAzanAlarm(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.toggleAzanAlarm(enabled)
        }
    }

    fun playTestAzan() {
        val app = getApplication<Application>()
        AzanAlarmSoundManager.playAzan(app, "টেস্ট ওয়াক্ত", isTest = true)
    }

    fun stopAzanAudio() {
        AzanAlarmSoundManager.stopAzan()
    }

    fun togglePrayerForDate(dateStr: String, prayerType: PrayerType) {
        viewModelScope.launch(Dispatchers.IO) {
            prayerRepository.togglePrayerForDate(dateStr, prayerType)
            vibrate(50)
        }
    }

    fun setSelectedDate(dateStr: String) {
        _selectedDateString.value = dateStr
    }

    fun goToToday() {
        _selectedDateString.value = prayerRepository.getTodayDateString(userSettings.value.timeZoneId)
    }

    fun changeDateOffset(offsetDays: Int) {
        try {
            val tz = java.util.TimeZone.getTimeZone(userSettings.value.timeZoneId)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).apply {
                timeZone = tz
            }
            val current = sdf.parse(_selectedDateString.value) ?: Date()
            val cal = Calendar.getInstance(tz).apply {
                time = current
                add(Calendar.DAY_OF_YEAR, offsetDays)
            }
            _selectedDateString.value = sdf.format(cal.time)
        } catch (e: Exception) {
            goToToday()
        }
    }

    // --- Tasbih Actions ---
    fun selectTasbih(index: Int) {
        _selectedTasbihIndex.value = index
    }

    fun countTasbih() {
        val items = tasbihItems.value
        val index = _selectedTasbihIndex.value
        if (index in items.indices) {
            val item = items[index]
            viewModelScope.launch(Dispatchers.IO) {
                val cycleCompleted = prayerRepository.incrementTasbih(item)
                if (cycleCompleted) {
                    vibratePattern(longArrayOf(0, 100, 100, 150))
                } else {
                    vibrate(40)
                }
            }
        }
    }

    fun decrementTasbih() {
        val items = tasbihItems.value
        val index = _selectedTasbihIndex.value
        if (index in items.indices) {
            val item = items[index]
            viewModelScope.launch(Dispatchers.IO) {
                prayerRepository.decrementTasbih(item)
                vibrate(30)
            }
        }
    }

    fun setTasbihTarget(target: Int) {
        val items = tasbihItems.value
        val index = _selectedTasbihIndex.value
        if (index in items.indices) {
            val item = items[index]
            viewModelScope.launch(Dispatchers.IO) {
                prayerRepository.setTasbihTarget(item.id, target)
            }
        }
    }

    fun setTasbihCount(count: Int) {
        val items = tasbihItems.value
        val index = _selectedTasbihIndex.value
        if (index in items.indices) {
            val item = items[index]
            viewModelScope.launch(Dispatchers.IO) {
                prayerRepository.setTasbihCount(item.id, count)
                vibrate(50)
            }
        }
    }

    fun resetCurrentTasbih() {
        val items = tasbihItems.value
        val index = _selectedTasbihIndex.value
        if (index in items.indices) {
            val item = items[index]
            viewModelScope.launch(Dispatchers.IO) {
                prayerRepository.resetTasbih(item.id)
                vibrate(60)
            }
        }
    }

    fun resetCurrentTasbihAndCycles() {
        val items = tasbihItems.value
        val index = _selectedTasbihIndex.value
        if (index in items.indices) {
            val item = items[index]
            viewModelScope.launch(Dispatchers.IO) {
                prayerRepository.resetTasbihAndCycles(item.id)
                vibrate(80)
            }
        }
    }

    fun saveCurrentTasbihSession(note: String = "") {
        val items = tasbihItems.value
        val index = _selectedTasbihIndex.value
        if (index in items.indices) {
            val item = items[index]
            viewModelScope.launch(Dispatchers.IO) {
                tasbihRecordRepository.saveRecord(
                    dhikrTitle = item.titleBangla,
                    count = item.currentCount,
                    targetCount = item.targetCount,
                    totalCycles = item.totalCompletedCycles,
                    note = note
                )
                vibratePattern(longArrayOf(0, 70, 70, 100))
            }
        }
    }

    fun deleteSavedTasbihRecord(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tasbihRecordRepository.deleteRecord(id)
        }
    }

    fun clearAllSavedTasbihRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            tasbihRecordRepository.clearAllRecords()
        }
    }

    fun addCustomTasbih(title: String, arabic: String, meaning: String, target: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = prayerRepository.addCustomTasbih(title, arabic, meaning, target)
            val updated = prayerRepository.getAllTasbihItems().first()
            val newIdx = updated.indexOfFirst { it.id == id }
            if (newIdx >= 0) {
                _selectedTasbihIndex.value = newIdx
            }
        }
    }

    fun deleteCustomTasbih(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            prayerRepository.deleteCustomTasbih(id)
            _selectedTasbihIndex.value = 0
        }
    }

    fun vibrate(durationMillis: Long) {
        if (!userSettings.value.tasbihVibration) return
        val context = getApplication<Application>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator?.vibrate(
                VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMillis)
            }
        }
    }

    fun vibratePattern(timings: LongArray) {
        if (!userSettings.value.tasbihVibration) return
        val context = getApplication<Application>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator?.vibrate(
                VibrationEffect.createWaveform(timings, -1)
            )
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createWaveform(timings, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(timings, -1)
            }
        }
    }
}
