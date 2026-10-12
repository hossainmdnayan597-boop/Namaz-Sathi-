package com.example.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerDao {

    // --- Prayer Log ---
    @Query("SELECT * FROM prayer_logs WHERE date = :date LIMIT 1")
    fun getLogForDate(date: String): Flow<PrayerLog?>

    @Query("SELECT * FROM prayer_logs WHERE date = :date LIMIT 1")
    suspend fun getLogForDateSync(date: String): PrayerLog?

    @Query("SELECT * FROM prayer_logs WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getLogsInRange(startDate: String, endDate: String): Flow<List<PrayerLog>>

    @Query("SELECT * FROM prayer_logs ORDER BY date DESC LIMIT 60")
    fun getRecentLogs(): Flow<List<PrayerLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePrayerLog(log: PrayerLog): Long

    @Query("DELETE FROM prayer_logs WHERE date = :date")
    suspend fun deletePrayerLog(date: String)

    // --- Tasbih Items ---
    @Query("SELECT * FROM tasbih_items ORDER BY id ASC")
    fun getAllTasbihItems(): Flow<List<TasbihItem>>

    @Query("SELECT * FROM tasbih_items WHERE id = :id LIMIT 1")
    fun getTasbihItemById(id: Long): Flow<TasbihItem?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasbihItem(item: TasbihItem): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDefaultTasbihItems(items: List<TasbihItem>)

    @Update
    suspend fun updateTasbihItem(item: TasbihItem)

    @Query("UPDATE tasbih_items SET currentCount = :count WHERE id = :id")
    suspend fun updateTasbihCount(id: Long, count: Int)

    @Query("UPDATE tasbih_items SET currentCount = :count, totalCompletedCycles = :cycles WHERE id = :id")
    suspend fun updateTasbihCountAndCycles(id: Long, count: Int, cycles: Int)

    @Query("UPDATE tasbih_items SET targetCount = :target WHERE id = :id")
    suspend fun updateTasbihTarget(id: Long, target: Int)

    @Query("DELETE FROM tasbih_items WHERE id = :id AND isCustom = 1")
    suspend fun deleteCustomTasbih(id: Long)
}
