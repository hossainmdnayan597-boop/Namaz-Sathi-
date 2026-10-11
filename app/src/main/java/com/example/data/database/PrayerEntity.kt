package com.example.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "prayer_logs",
    indices = [Index(value = ["date"], unique = true)]
)
data class PrayerLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // Format "yyyy-MM-dd"
    val fajr: Boolean = false,
    val dhuhr: Boolean = false,
    val asr: Boolean = false,
    val maghrib: Boolean = false,
    val isha: Boolean = false,
    val tahajjud: Boolean = false,
    val notes: String = ""
) {
    val completedCount: Int
        get() = (if (fajr) 1 else 0) +
                (if (dhuhr) 1 else 0) +
                (if (asr) 1 else 0) +
                (if (maghrib) 1 else 0) +
                (if (isha) 1 else 0)

    val isAllFardCompleted: Boolean
        get() = completedCount == 5
}

@Entity(tableName = "tasbih_items")
data class TasbihItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titleBangla: String,
    val arabic: String,
    val meaningBangla: String,
    val targetCount: Int = 33,
    val currentCount: Int = 0,
    val totalCompletedCycles: Int = 0,
    val isCustom: Boolean = false
)
