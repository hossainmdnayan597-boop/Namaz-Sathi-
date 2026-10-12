package com.example.data.database

import com.example.data.prayer.PrayerType
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PrayerRepository(private val prayerDao: PrayerDao) {

    fun getTodayDateString(timeZoneId: String = "Asia/Dhaka"): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).apply {
            timeZone = java.util.TimeZone.getTimeZone(timeZoneId)
        }
        return sdf.format(Date())
    }

    fun getLogForDate(date: String): Flow<PrayerLog?> {
        return prayerDao.getLogForDate(date)
    }

    fun getRecentLogs(): Flow<List<PrayerLog>> {
        return prayerDao.getRecentLogs()
    }

    suspend fun togglePrayerForDate(date: String, prayerType: PrayerType): PrayerLog {
        val existing = prayerDao.getLogForDateSync(date) ?: PrayerLog(date = date)
        val updated = when (prayerType) {
            PrayerType.FAJR -> existing.copy(fajr = !existing.fajr)
            PrayerType.DHUHR -> existing.copy(dhuhr = !existing.dhuhr)
            PrayerType.ASR -> existing.copy(asr = !existing.asr)
            PrayerType.MAGHRIB -> existing.copy(maghrib = !existing.maghrib)
            PrayerType.ISHA -> existing.copy(isha = !existing.isha)
            PrayerType.TAHAJJUD -> existing.copy(tahajjud = !existing.tahajjud)
            PrayerType.SUNRISE -> existing
        }
        prayerDao.insertOrUpdatePrayerLog(updated)
        return updated
    }

    suspend fun savePrayerNotes(date: String, notes: String) {
        val existing = prayerDao.getLogForDateSync(date) ?: PrayerLog(date = date)
        prayerDao.insertOrUpdatePrayerLog(existing.copy(notes = notes))
    }

    fun getAllTasbihItems(): Flow<List<TasbihItem>> {
        return prayerDao.getAllTasbihItems()
    }

    suspend fun incrementTasbih(item: TasbihItem): Boolean {
        val newCount = item.currentCount + 1
        val cycleCompleted = item.targetCount > 0 && newCount >= item.targetCount
        val completedCycles = if (cycleCompleted) {
            item.totalCompletedCycles + 1
        } else {
            item.totalCompletedCycles
        }
        val finalCount = if (cycleCompleted) 0 else newCount
        prayerDao.updateTasbihCountAndCycles(item.id, finalCount, completedCycles)
        return cycleCompleted
    }

    suspend fun decrementTasbih(item: TasbihItem) {
        if (item.currentCount > 0) {
            prayerDao.updateTasbihCount(item.id, item.currentCount - 1)
        }
    }

    suspend fun setTasbihCount(id: Long, count: Int) {
        prayerDao.updateTasbihCount(id, maxOf(0, count))
    }

    suspend fun setTasbihTarget(id: Long, target: Int) {
        prayerDao.updateTasbihTarget(id, maxOf(0, target))
    }

    suspend fun resetTasbih(id: Long) {
        prayerDao.updateTasbihCount(id, 0)
    }

    suspend fun resetTasbihAndCycles(id: Long) {
        prayerDao.updateTasbihCountAndCycles(id, 0, 0)
    }

    suspend fun addCustomTasbih(titleBangla: String, arabic: String, meaningBangla: String, targetCount: Int): Long {
        return prayerDao.insertTasbihItem(
            TasbihItem(
                titleBangla = titleBangla,
                arabic = arabic,
                meaningBangla = meaningBangla,
                targetCount = targetCount,
                isCustom = true
            )
        )
    }

    suspend fun deleteCustomTasbih(id: Long) {
        prayerDao.deleteCustomTasbih(id)
    }

    suspend fun ensureDefaultTasbihsIfEmpty() {
        AppDatabase.populateInitialTasbihs(prayerDao)
    }
}
