package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [PrayerLog::class, TasbihItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun prayerDao(): PrayerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope = CoroutineScope(Dispatchers.IO)): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "namaz_sathi_database"
                )
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialTasbihs(database.prayerDao())
                    }
                }
            }
        }

        suspend fun populateInitialTasbihs(dao: PrayerDao) {
            val defaults = listOf(
                TasbihItem(
                    titleBangla = "সুবহানাল্লাহ",
                    arabic = "سُبْحَانَ اللَّهِ",
                    meaningBangla = "আল্লাহ মহাপবিত্র ও নিষ্কলুষ",
                    targetCount = 33,
                    isCustom = false
                ),
                TasbihItem(
                    titleBangla = "আলহামদুলিল্লাহ",
                    arabic = "الْحَمْدُ لِلَّهِ",
                    meaningBangla = "সমস্ত প্রশংসা একমাত্র আল্লাহর জন্য",
                    targetCount = 33,
                    isCustom = false
                ),
                TasbihItem(
                    titleBangla = "আল্লাহু আকবার",
                    arabic = "اللَّهُ أَكْبَرُ",
                    meaningBangla = "আল্লাহ সর্বশ্রেষ্ঠ ও মহান",
                    targetCount = 34,
                    isCustom = false
                ),
                TasbihItem(
                    titleBangla = "লা ইলাহা ইল্লাল্লাহ",
                    arabic = "لَا إِلٰهَ إِلَّا اللَّهُ",
                    meaningBangla = "আল্লাহ ছাড়া কোনো সত্য উপাস্য নেই",
                    targetCount = 100,
                    isCustom = false
                ),
                TasbihItem(
                    titleBangla = "আস্তাগফিরুল্লাহ",
                    arabic = "أَسْتَغْفِرُ اللَّهَ",
                    meaningBangla = "আমি আল্লাহর কাছে ক্ষমাপ্রার্থনা করছি",
                    targetCount = 100,
                    isCustom = false
                ),
                TasbihItem(
                    titleBangla = "দুরূদ শরীফ",
                    arabic = "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ",
                    meaningBangla = "হে আল্লাহ! হযরত মুহাম্মদ (সাঃ)-এর ওপর রহমত বর্ষণ করুন",
                    targetCount = 100,
                    isCustom = false
                ),
                TasbihItem(
                    titleBangla = "কালিমায়ে তামজীদ",
                    arabic = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ سُبْحَانَ اللَّهِ الْعَظِيمِ",
                    meaningBangla = "আল্লাহর প্রশংসাসহ পবিত্রতা বর্ণনা করছি, মহান আল্লাহ অতীব পবিত্র",
                    targetCount = 100,
                    isCustom = false
                ),
                TasbihItem(
                    titleBangla = "লা হাওলা ওয়ালা কুওয়্যাতা",
                    arabic = "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ",
                    meaningBangla = "আল্লাহর সাহায্য ব্যতীত কোনো শক্তি বা সামর্থ্য নেই",
                    targetCount = 100,
                    isCustom = false
                )
            )
            dao.insertDefaultTasbihItems(defaults)
        }
    }
}
