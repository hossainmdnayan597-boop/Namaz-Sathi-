package com.example.data.tasbih

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class TasbihRecord(
    val id: String = UUID.randomUUID().toString(),
    val dhikrTitle: String,
    val count: Int,
    val targetCount: Int,
    val totalCycles: Int,
    val timestampMillis: Long = System.currentTimeMillis(),
    val formattedDate: String = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale("bn", "BD")).format(Date()),
    val note: String = ""
)

class TasbihRecordRepository(private val context: Context) {

    private val prefs = context.getSharedPreferences("tasbih_saved_records", Context.MODE_PRIVATE)
    private val keyRecords = "saved_records_json"

    private val _records = MutableStateFlow<List<TasbihRecord>>(emptyList())
    val records: StateFlow<List<TasbihRecord>> = _records.asStateFlow()

    init {
        loadRecords()
    }

    private fun loadRecords() {
        val jsonStr = prefs.getString(keyRecords, null) ?: "[]"
        try {
            val jsonArray = JSONArray(jsonStr)
            val list = mutableListOf<TasbihRecord>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    TasbihRecord(
                        id = obj.optString("id", UUID.randomUUID().toString()),
                        dhikrTitle = obj.optString("dhikrTitle", ""),
                        count = obj.optInt("count", 0),
                        targetCount = obj.optInt("targetCount", 33),
                        totalCycles = obj.optInt("totalCycles", 0),
                        timestampMillis = obj.optLong("timestampMillis", System.currentTimeMillis()),
                        formattedDate = obj.optString("formattedDate", ""),
                        note = obj.optString("note", "")
                    )
                )
            }
            _records.value = list.sortedByDescending { it.timestampMillis }
        } catch (e: Exception) {
            _records.value = emptyList()
        }
    }

    suspend fun saveRecord(
        dhikrTitle: String,
        count: Int,
        targetCount: Int,
        totalCycles: Int,
        note: String = ""
    ): TasbihRecord = withContext(Dispatchers.IO) {
        val newRecord = TasbihRecord(
            id = UUID.randomUUID().toString(),
            dhikrTitle = dhikrTitle,
            count = count,
            targetCount = targetCount,
            totalCycles = totalCycles,
            timestampMillis = System.currentTimeMillis(),
            formattedDate = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale("bn", "BD")).format(Date()),
            note = note
        )
        val currentList = _records.value.toMutableList()
        currentList.add(0, newRecord)
        persistList(currentList)
        _records.value = currentList
        newRecord
    }

    suspend fun deleteRecord(id: String) = withContext(Dispatchers.IO) {
        val currentList = _records.value.filter { it.id != id }
        persistList(currentList)
        _records.value = currentList
    }

    suspend fun clearAllRecords() = withContext(Dispatchers.IO) {
        persistList(emptyList())
        _records.value = emptyList()
    }

    private fun persistList(list: List<TasbihRecord>) {
        val jsonArray = JSONArray()
        for (item in list) {
            val obj = JSONObject().apply {
                put("id", item.id)
                put("dhikrTitle", item.dhikrTitle)
                put("count", item.count)
                put("targetCount", item.targetCount)
                put("totalCycles", item.totalCycles)
                put("timestampMillis", item.timestampMillis)
                put("formattedDate", item.formattedDate)
                put("note", item.note)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString(keyRecords, jsonArray.toString()).apply()
    }
}
