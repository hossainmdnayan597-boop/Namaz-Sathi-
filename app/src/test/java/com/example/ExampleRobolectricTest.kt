package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.database.AppDatabase
import com.example.data.database.PrayerLog
import com.example.data.database.PrayerRepository
import com.example.data.database.TasbihItem
import com.example.data.learning.NamazLearningData
import com.example.data.prayer.*
import com.example.data.qibla.QiblaCalculator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var db: AppDatabase
    private lateinit var repository: PrayerRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = PrayerRepository(db.prayerDao())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `read string from context verifies Namaz Sathi`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Namaz Sathi", appName)
    }

    @Test
    fun `test offline prayer calculation sequence`() {
        // Calculate for Dhaka on Oct 10, 2026
        val schedule = PrayerCalculator.calculatePrayerTimes(
            year = 2026,
            month = 10,
            day = 10,
            latitude = 23.8103,
            longitude = 90.4125,
            timeZoneOffsetHours = 6.0,
            method = CalculationMethod.KARACHI,
            juristic = JuristicMethod.HANAFI
        )

        // Strict chronological ordering
        assertTrue(schedule.fajr.timeMillis < schedule.sunrise.timeMillis)
        assertTrue(schedule.sunrise.timeMillis < schedule.dhuhr.timeMillis)
        assertTrue(schedule.dhuhr.timeMillis < schedule.asr.timeMillis)
        assertTrue(schedule.asr.timeMillis < schedule.maghrib.timeMillis)
        assertTrue(schedule.maghrib.timeMillis < schedule.isha.timeMillis)
        assertTrue(schedule.sehriEndTime.timeMillis < schedule.fajr.timeMillis)
    }

    @Test
    fun `test real Bangladesh time zone calculations and formatting`() {
        val schedule = PrayerCalculator.calculatePrayerTimes(
            year = 2026,
            month = 10,
            day = 10,
            latitude = 23.8103,
            longitude = 90.4125,
            timeZoneOffsetHours = 6.0,
            timeZoneId = "Asia/Dhaka",
            method = CalculationMethod.KARACHI,
            juristic = JuristicMethod.HANAFI
        )

        assertEquals("Asia/Dhaka", schedule.timeZoneId)
        assertTrue(schedule.fajr.formattedTime12h.contains("AM"))
        assertTrue(schedule.maghrib.formattedTime12h.contains("PM"))
        assertTrue(schedule.fajr.formattedBanglaTime.isNotEmpty())
    }

    @Test
    fun `test qibla calculation for Dhaka`() {
        // From Dhaka (~23.81 N, 90.41 E) to Kaaba, bearing is approximately 274-275 degrees (West-Northwest)
        val bearing = QiblaCalculator.calculateQiblaBearing(23.8103, 90.4125)
        assertTrue("Bearing should be between 270 and 280 degrees", bearing in 270.0..280.0)

        val distance = QiblaCalculator.calculateDistanceToKaaba(23.8103, 90.4125)
        assertTrue("Distance to Kaaba from Dhaka is around 5000km", distance in 4800.0..5400.0)
    }

    @Test
    fun `test room prayer tracker toggle and persistence`() = runBlocking {
        val testDate = "2026-10-10"

        // Initially no record
        val initialLog = repository.getLogForDate(testDate).first()
        assertNull(initialLog)

        // Toggle Fajr
        repository.togglePrayerForDate(testDate, PrayerType.FAJR)
        val afterFajr = repository.getLogForDate(testDate).first()
        assertNotNull(afterFajr)
        assertTrue(afterFajr!!.fajr)
        assertFalse(afterFajr.dhuhr)
        assertEquals(1, afterFajr.completedCount)

        // Toggle Dhuhr & Asr
        repository.togglePrayerForDate(testDate, PrayerType.DHUHR)
        repository.togglePrayerForDate(testDate, PrayerType.ASR)
        val afterAsr = repository.getLogForDate(testDate).first()!!
        assertEquals(3, afterAsr.completedCount)

        // Untoggle Fajr
        repository.togglePrayerForDate(testDate, PrayerType.FAJR)
        val afterUncheck = repository.getLogForDate(testDate).first()!!
        assertFalse(afterUncheck.fajr)
        assertEquals(2, afterUncheck.completedCount)
    }

    @Test
    fun `test tasbih item creation and cycle increment`() = runBlocking {
        val customId = repository.addCustomTasbih(
            titleBangla = "লা ইলাহা ইল্লাল্লাহ",
            arabic = "لَا إِلٰهَ إِلَّা اللَّهُ",
            meaningBangla = "আল্লাহ ছাড়া কোনো সত্য উপাস্য নেই",
            targetCount = 3
        )
        assertTrue(customId > 0)

        var items = repository.getAllTasbihItems().first()
        val item = items.first { it.id == customId }
        assertEquals(0, item.currentCount)

        // Count 1
        repository.incrementTasbih(item)
        items = repository.getAllTasbihItems().first()
        assertEquals(1, items.first { it.id == customId }.currentCount)

        // Count 2
        repository.incrementTasbih(items.first { it.id == customId })
        items = repository.getAllTasbihItems().first()
        assertEquals(2, items.first { it.id == customId }.currentCount)

        // Count 3 -> completes cycle of target 3 and resets to 0
        val cycleCompleted = repository.incrementTasbih(items.first { it.id == customId })
        assertTrue(cycleCompleted)
        items = repository.getAllTasbihItems().first()
        val completedItem = items.first { it.id == customId }
        assertEquals(0, completedItem.currentCount)
        assertEquals(1, completedItem.totalCompletedCycles)
    }

    @Test
    fun `test city search returns valid results`() {
        val dhakaResults = CityData.searchCities("ঢাকা")
        assertTrue(dhakaResults.any { it.nameBangla == "ঢাকা" })

        val ctgResults = CityData.searchCities("Chittagong")
        assertTrue(ctgResults.any { it.nameEnglish == "Chittagong" })

        val allCount = CityData.ALL_CITIES.size
        assertTrue("All cities should have at least 64 districts plus world cities", allCount >= 64)
    }

    @Test
    fun `test Bengali Namaz learning categories verified`() {
        val categories = NamazLearningData.CATEGORIES
        assertTrue(categories.isNotEmpty())

        val wuduCat = categories.find { it.id == "wudu_purity" }
        assertNotNull(wuduCat)
        assertTrue(wuduCat!!.items.any { it.id == "wudu_farz" })

        val duasCat = categories.find { it.id == "essential_duas" }
        assertNotNull(duasCat)
        assertTrue(duasCat!!.items.any { it.id == "dua_tashahhud" })
        assertTrue(duasCat.items.any { it.id == "dua_darood" })
    }

    @Test
    fun `test 30th Para Surahs have Arabic text, Bangla uchharon and meaning`() {
        val categories = NamazLearningData.CATEGORIES
        val surahsCat = categories.find { it.id == "short_surahs" }
        assertNotNull(surahsCat)
        assertTrue("Should have comprehensive 30th Para Surahs", surahsCat!!.items.size >= 15)

        val surahNas = surahsCat.items.find { it.id == "surah_nas" }
        assertNotNull(surahNas)
        assertNotNull(surahNas!!.transliterationBangla)
        assertTrue(surahNas.transliterationBangla!!.contains("ক্বুল আ'ঊযু"))

        val surahIkhlas = surahsCat.items.find { it.id == "surah_ikhlas" }
        assertNotNull(surahIkhlas)
        assertNotNull(surahIkhlas!!.transliterationBangla)
        assertTrue(surahIkhlas.transliterationBangla!!.contains("ক্বুল হুওয়াল্লাহু আহাদ"))

        val surahAsr = surahsCat.items.find { it.id == "surah_asr" }
        assertNotNull(surahAsr)
        assertNotNull(surahAsr!!.transliterationBangla)
        assertTrue(surahAsr.transliterationBangla!!.contains("ওয়াল 'আসর"))

        val surahQadr = surahsCat.items.find { it.id == "surah_qadr" }
        assertNotNull(surahQadr)
        assertNotNull(surahQadr!!.transliterationBangla)
        assertTrue(surahQadr.transliterationBangla!!.contains("লাইলাতুল ক্বদর"))
    }

    @Test
    fun `test full Quran 114 Surahs catalog and 30th Para Juz Amma`() {
        val allSurahs = com.example.data.quran.QuranData.ALL_114_SURAHS
        assertEquals("Total Quran surahs must be 114", 114, allSurahs.size)

        // Verify Surah 1 Al-Fatihah
        val fatihah = allSurahs.first { it.number == 1 }
        assertEquals("আল-ফাতিহা", fatihah.nameBangla)
        assertNotNull(fatihah.arabicText)
        assertNotNull(fatihah.transliterationBangla)
        assertNotNull(fatihah.translationBangla)
        assertTrue(fatihah.transliterationBangla!!.contains("আলহামদু লিল্লাহি"))

        // Verify Surah 2 Al-Baqarah has Ayat-ul-Kursi
        val baqarah = allSurahs.first { it.number == 2 }
        assertNotNull(baqarah.arabicText)
        assertTrue(baqarah.transliterationBangla!!.contains("আল্লাহু লা-ইলাহা ইল্লা হুওয়াল হাইয়্যুল ক্বাইয়্যূম"))

        // Verify 30th Para Surah 78 An-Naba
        val anNaba = com.example.data.quran.QuranData.getFullSurah(78)
        assertEquals("আন-নাবা", anNaba.nameBangla)
        assertNotNull(anNaba.arabicText)
        assertTrue(anNaba.transliterationBangla!!.contains("আম্মা ইয়াতাসা-আলূন"))
        assertTrue(anNaba.translationBangla!!.contains("মহা সংবাদ"))

        // Verify Surah 114 An-Nas
        val anNas = com.example.data.quran.QuranData.getFullSurah(114)
        assertEquals("আন-নাস", anNas.nameBangla)
        assertTrue(anNas.transliterationBangla!!.contains("ক্বুল আ'ঊযু বিরাব্বিন নাস"))

        // Search test
        val searchResults = com.example.data.quran.QuranData.searchSurahs("কাহফ")
        assertTrue(searchResults.any { it.number == 18 })
    }

    @Test
    fun `test Sahih Hadith collection and search`() {
        val hadiths = com.example.data.hadith.HadithData.SAHIH_HADITHS
        assertTrue("Must contain Sahih Hadiths", hadiths.size >= 15)

        // Verify prayer hadith
        val prayerHadith = hadiths.find { it.categoryId == "prayer" }
        assertNotNull(prayerHadith)
        assertTrue(prayerHadith!!.translationBangla.contains("সালাত") || prayerHadith.translationBangla.contains("নামাজ"))

        // Verify search by keyword
        val searchResults = com.example.data.hadith.HadithData.searchHadiths("নিয়ত")
        assertTrue(searchResults.isNotEmpty())
        assertTrue(searchResults.first().translationBangla.contains("নিয়ত"))
    }
}
