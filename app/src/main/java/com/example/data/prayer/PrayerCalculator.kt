package com.example.data.prayer

import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.math.*

enum class CalculationMethod(
    val titleBangla: String,
    val titleEnglish: String,
    val fajrAngle: Double,
    val ishaAngle: Double,
    val isIshaFixedMinutes: Boolean = false,
    val ishaFixedMinutes: Int = 0
) {
    KARACHI(
        titleBangla = "ইসলামিক ফাউন্ডেশন / করাচি (১৮°, ১৮°)",
        titleEnglish = "Islamic Foundation BD / Karachi (18°, 18°)",
        fajrAngle = 18.0,
        ishaAngle = 18.0
    ),
    MWL(
        titleBangla = "মুসলিম ওয়ার্ল্ড লীগ (১৮°, ১৭°)",
        titleEnglish = "Muslim World League (18°, 17°)",
        fajrAngle = 18.0,
        ishaAngle = 17.0
    ),
    UMM_AL_QURA(
        titleBangla = "উম্মুল কুরা, মক্কা (১৮.৫°, ৯০ মি.)",
        titleEnglish = "Umm Al-Qura, Makkah (18.5°, 90 min)",
        fajrAngle = 18.5,
        ishaAngle = 0.0,
        isIshaFixedMinutes = true,
        ishaFixedMinutes = 90
    ),
    EGYPTIAN(
        titleBangla = "মিশরীয় জেনারেল অথরিটি (১৯.৫°, ১৭.৫°)",
        titleEnglish = "Egyptian General Authority (19.5°, 17.5°)",
        fajrAngle = 19.5,
        ishaAngle = 17.5
    ),
    ISNA(
        titleBangla = "উত্তর আমেরিকা ইসলামিক সোসাইটি (১৫°, ১৫°)",
        titleEnglish = "ISNA, North America (15°, 15°)",
        fajrAngle = 15.0,
        ishaAngle = 15.0
    ),
    DUBAI(
        titleBangla = "দুবাই / উপসাগরীয় অঞ্চল (১৮.২°, ৯০ মি.)",
        titleEnglish = "Dubai / Gulf Region (18.2°, 90 min)",
        fajrAngle = 18.2,
        ishaAngle = 0.0,
        isIshaFixedMinutes = true,
        ishaFixedMinutes = 90
    )
}

enum class JuristicMethod(val titleBangla: String, val shadowFactor: Double) {
    HANAFI("হানাফী (দ্বিগুণ ছায়া)", 2.0),
    SHAFI("শাফেয়ী / মালেকী / হাম্বলী (একগুণ ছায়া)", 1.0)
}

enum class HighLatitudeRule {
    MIDDLE_OF_NIGHT,
    ONE_SEVENTH,
    ANGLE_BASED
}

enum class PrayerType(
    val englishName: String,
    val banglaName: String,
    val arabicName: String,
    val rakatsSummary: String
) {
    FAJR("Fajr", "ফজর", "الفجر", "২ রাকাত সুন্নত, ২ রাকাত ফরজ"),
    SUNRISE("Sunrise", "সূর্যোদয় (ইশরাক শুরু)", "الشروق", "নফল ইশরাক / চাশত"),
    DHUHR("Dhuhr", "যোহর", "الظهر", "৪ সুন্নত, ৪ ফরজ, ২ সুন্নত, ২ নফল"),
    ASR("Asr", "আসর", "العصر", "৪ সুন্নতে গায়রে মুয়াক্কাদা, ৪ ফরজ"),
    MAGHRIB("Maghrib", "মাগরিব", "المغرب", "৩ ফরজ, ২ সুন্নত, ২ নফল"),
    ISHA("Isha", "এশা", "العشاء", "৪ ফরজ, ২ সুন্নত, ২ নফল, ৩ বিতর"),
    TAHAJJUD("Tahajjud", "তাহাজ্জুদ ও সেহরি শেষ", "التهجد", "শেষ তৃতীয়াংশ নফল / সেহরির শেষ সময়")
}

data class PrayerTimeItem(
    val type: PrayerType,
    val timeMillis: Long,
    val formattedTime12h: String,
    val formattedBanglaTime: String
)

data class DayPrayerSchedule(
    val dateString: String,
    val timeZoneId: String,
    val fajr: PrayerTimeItem,
    val sunrise: PrayerTimeItem,
    val dhuhr: PrayerTimeItem,
    val asr: PrayerTimeItem,
    val maghrib: PrayerTimeItem,
    val isha: PrayerTimeItem,
    val tahajjudEnd: PrayerTimeItem,
    val iftarTime: PrayerTimeItem, // Equal to Maghrib
    val sehriEndTime: PrayerTimeItem // ~10 mins before Fajr
) {
    fun toList(): List<PrayerTimeItem> = listOf(
        fajr, sunrise, dhuhr, asr, maghrib, isha
    )
}

data class CurrentPrayerStatus(
    val currentPrayer: PrayerType,
    val nextPrayer: PrayerType,
    val nextPrayerTimeMillis: Long,
    val remainingMillis: Long,
    val progressFraction: Float, // 0.0f to 1.0f progress of current prayer time window
    val isPrayerTimeNow: Boolean
)

object PrayerCalculator {

    val BANGLADESH_TIME_ZONE: TimeZone = TimeZone.getTimeZone("Asia/Dhaka")

    private fun d2r(d: Double): Double = d * Math.PI / 180.0
    private fun r2d(r: Double): Double = r * 180.0 / Math.PI

    private fun fixAngle(a: Double): Double {
        var res = a - 360.0 * floor(a / 360.0)
        if (res < 0) res += 360.0
        return res
    }

    private fun fixHour(a: Double): Double {
        var res = a - 24.0 * floor(a / 24.0)
        if (res < 0) res += 24.0
        return res
    }

    fun calculatePrayerTimes(
        year: Int,
        month: Int, // 1 - 12
        day: Int,
        latitude: Double,
        longitude: Double,
        timeZoneOffsetHours: Double = 6.0,
        timeZoneId: String = "Asia/Dhaka",
        method: CalculationMethod = CalculationMethod.KARACHI,
        juristic: JuristicMethod = JuristicMethod.HANAFI,
        highLatRule: HighLatitudeRule = HighLatitudeRule.ANGLE_BASED
    ): DayPrayerSchedule {
        val timeZone = TimeZone.getTimeZone(timeZoneId)

        // Julian Day calculation
        var y = year
        var m = month
        if (m <= 2) {
            y -= 1
            m += 12
        }
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        val jd = floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5

        // Solar coordinates
        val d = jd - 2451545.0
        val g = fixAngle(357.529 + 0.98560028 * d)
        val q = fixAngle(280.459 + 0.98564736 * d)
        val l = fixAngle(q + 1.915 * sin(d2r(g)) + 0.020 * sin(d2r(2 * g)))
        val e = 23.439 - 0.00000036 * d

        val ra = fixAngle(r2d(atan2(cos(d2r(e)) * sin(d2r(l)), cos(d2r(l))))) / 15.0
        val declination = r2d(asin(sin(d2r(e)) * sin(d2r(l))))
        val eqt = q / 15.0 - ra

        // Midday (Dhuhr)
        val dhuhrUtc = 12.0 - eqt - (longitude / 15.0)
        val dhuhrLocal = fixHour(dhuhrUtc + timeZoneOffsetHours)

        // Helper for Hour Angle
        fun hourAngle(altitudeAngle: Double): Double {
            val sinAlt = sin(d2r(altitudeAngle))
            val sinLat = sin(d2r(latitude))
            val cosLat = cos(d2r(latitude))
            val sinDec = sin(d2r(declination))
            val cosDec = cos(d2r(declination))

            val cosT = (sinAlt - sinLat * sinDec) / (cosLat * cosDec)
            if (cosT > 1.0 || cosT < -1.0) {
                return Double.NaN
            }
            return r2d(acos(cosT)) / 15.0
        }

        // Sunrise and Sunset (standard sun angle -0.8333 degrees)
        var tSunrise = hourAngle(-0.8333)
        if (tSunrise.isNaN()) tSunrise = 6.0
        val sunriseLocal = fixHour(dhuhrLocal - tSunrise)
        val sunsetLocal = fixHour(dhuhrLocal + tSunrise)

        // Fajr
        var tFajr = hourAngle(-method.fajrAngle)
        if (tFajr.isNaN()) {
            tFajr = when (highLatRule) {
                HighLatitudeRule.MIDDLE_OF_NIGHT -> tSunrise / 2.0
                HighLatitudeRule.ONE_SEVENTH -> tSunrise / 7.0
                HighLatitudeRule.ANGLE_BASED -> (method.fajrAngle / 60.0) * tSunrise
            }
        }
        val fajrLocal = fixHour(dhuhrLocal - tFajr)

        // Isha
        val ishaLocal: Double = if (method.isIshaFixedMinutes) {
            fixHour(sunsetLocal + (method.ishaFixedMinutes / 60.0))
        } else {
            var tIsha = hourAngle(-method.ishaAngle)
            if (tIsha.isNaN()) {
                tIsha = when (highLatRule) {
                    HighLatitudeRule.MIDDLE_OF_NIGHT -> tSunrise / 2.0
                    HighLatitudeRule.ONE_SEVENTH -> tSunrise / 7.0
                    HighLatitudeRule.ANGLE_BASED -> (method.ishaAngle / 60.0) * tSunrise
                }
            }
            fixHour(dhuhrLocal + tIsha)
        }

        // Asr calculation
        val latDecDiff = abs(latitude - declination)
        val shadowRatio = juristic.shadowFactor + tan(d2r(latDecDiff))
        val asrAlt = r2d(atan(1.0 / shadowRatio))
        var tAsr = hourAngle(asrAlt)
        if (tAsr.isNaN()) tAsr = 3.0
        val asrLocal = fixHour(dhuhrLocal + tAsr)

        // Maghrib: sunset + ~2 minutes safety margin
        val maghribLocal = fixHour(sunsetLocal + (2.0 / 60.0))

        // Convert double hours to Calendar millis strictly inside target TimeZone
        fun toMillis(hours: Double): Long {
            val cal = Calendar.getInstance(timeZone).apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month - 1)
                set(Calendar.DAY_OF_MONTH, day)
                val h = hours.toInt()
                val mRem = (hours - h) * 60.0
                val minute = mRem.toInt()
                val second = ((mRem - minute) * 60.0).toInt()
                set(Calendar.HOUR_OF_DAY, h)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, second)
                set(Calendar.MILLISECOND, 0)
            }
            return cal.timeInMillis
        }

        val fajrMillis = toMillis(fajrLocal)
        val sunriseMillis = toMillis(sunriseLocal)
        val dhuhrMillis = toMillis(dhuhrLocal)
        val asrMillis = toMillis(asrLocal)
        val maghribMillis = toMillis(maghribLocal)
        val ishaMillis = toMillis(ishaLocal)

        // Sehri ends 10 mins before Fajr
        val sehriEndMillis = fajrMillis - (10 * 60 * 1000L)
        // Tahajjud is recommended in the last third of the night
        val tahajjudMillis = fajrMillis - (2 * 60 * 60 * 1000L)

        fun createItem(type: PrayerType, millis: Long): PrayerTimeItem {
            return PrayerTimeItem(
                type = type,
                timeMillis = millis,
                formattedTime12h = format12h(millis, timeZone),
                formattedBanglaTime = formatBangla(millis, timeZone)
            )
        }

        val dateStr = String.format("%04d-%02d-%02d", year, month, day)

        return DayPrayerSchedule(
            dateString = dateStr,
            timeZoneId = timeZoneId,
            fajr = createItem(PrayerType.FAJR, fajrMillis),
            sunrise = createItem(PrayerType.SUNRISE, sunriseMillis),
            dhuhr = createItem(PrayerType.DHUHR, dhuhrMillis),
            asr = createItem(PrayerType.ASR, asrMillis),
            maghrib = createItem(PrayerType.MAGHRIB, maghribMillis),
            isha = createItem(PrayerType.ISHA, ishaMillis),
            tahajjudEnd = createItem(PrayerType.TAHAJJUD, tahajjudMillis),
            iftarTime = createItem(PrayerType.MAGHRIB, maghribMillis),
            sehriEndTime = createItem(PrayerType.FAJR, sehriEndMillis)
        )
    }

    fun format12h(millis: Long, timeZone: TimeZone = BANGLADESH_TIME_ZONE): String {
        val cal = Calendar.getInstance(timeZone).apply { timeInMillis = millis }
        val hour24 = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        val ampm = if (hour24 < 12) "AM" else "PM"
        val hour12 = if (hour24 % 12 == 0) 12 else hour24 % 12
        return String.format("%02d:%02d %s", hour12, minute, ampm)
    }

    fun formatBangla(millis: Long, timeZone: TimeZone = BANGLADESH_TIME_ZONE): String {
        val eng = format12h(millis, timeZone)
        val banglaDigits = mapOf(
            '0' to '০', '1' to '১', '2' to '২', '3' to '৩', '4' to '৪',
            '5' to '৫', '6' to '৬', '7' to '৭', '8' to '৮', '9' to '৯'
        )
        val sb = StringBuilder()
        for (ch in eng) {
            sb.append(banglaDigits[ch] ?: ch)
        }
        return sb.toString()
            .replace("AM", "পূর্বাহ্ণ")
            .replace("PM", "অপরাহ্ণ")
    }

    fun getCurrentPrayerStatus(nowMillis: Long, schedule: DayPrayerSchedule): CurrentPrayerStatus {
        val f = schedule.fajr.timeMillis
        val s = schedule.sunrise.timeMillis
        val d = schedule.dhuhr.timeMillis
        val a = schedule.asr.timeMillis
        val m = schedule.maghrib.timeMillis
        val i = schedule.isha.timeMillis

        return when {
            nowMillis < f -> {
                val duration = f - (i - 24 * 3600 * 1000L)
                val elapsed = nowMillis - (i - 24 * 3600 * 1000L)
                val progress = (elapsed.toDouble() / duration.toDouble()).coerceIn(0.0, 1.0).toFloat()
                CurrentPrayerStatus(
                    currentPrayer = PrayerType.ISHA,
                    nextPrayer = PrayerType.FAJR,
                    nextPrayerTimeMillis = f,
                    remainingMillis = (f - nowMillis).coerceAtLeast(0L),
                    progressFraction = progress,
                    isPrayerTimeNow = false
                )
            }
            nowMillis in f until s -> {
                val duration = s - f
                val elapsed = nowMillis - f
                CurrentPrayerStatus(
                    currentPrayer = PrayerType.FAJR,
                    nextPrayer = PrayerType.SUNRISE,
                    nextPrayerTimeMillis = s,
                    remainingMillis = s - nowMillis,
                    progressFraction = (elapsed.toDouble() / duration.toDouble()).coerceIn(0.0, 1.0).toFloat(),
                    isPrayerTimeNow = true
                )
            }
            nowMillis in s until d -> {
                val duration = d - s
                val elapsed = nowMillis - s
                CurrentPrayerStatus(
                    currentPrayer = PrayerType.SUNRISE,
                    nextPrayer = PrayerType.DHUHR,
                    nextPrayerTimeMillis = d,
                    remainingMillis = d - nowMillis,
                    progressFraction = (elapsed.toDouble() / duration.toDouble()).coerceIn(0.0, 1.0).toFloat(),
                    isPrayerTimeNow = false
                )
            }
            nowMillis in d until a -> {
                val duration = a - d
                val elapsed = nowMillis - d
                CurrentPrayerStatus(
                    currentPrayer = PrayerType.DHUHR,
                    nextPrayer = PrayerType.ASR,
                    nextPrayerTimeMillis = a,
                    remainingMillis = a - nowMillis,
                    progressFraction = (elapsed.toDouble() / duration.toDouble()).coerceIn(0.0, 1.0).toFloat(),
                    isPrayerTimeNow = true
                )
            }
            nowMillis in a until m -> {
                val duration = m - a
                val elapsed = nowMillis - a
                CurrentPrayerStatus(
                    currentPrayer = PrayerType.ASR,
                    nextPrayer = PrayerType.MAGHRIB,
                    nextPrayerTimeMillis = m,
                    remainingMillis = m - nowMillis,
                    progressFraction = (elapsed.toDouble() / duration.toDouble()).coerceIn(0.0, 1.0).toFloat(),
                    isPrayerTimeNow = true
                )
            }
            nowMillis in m until i -> {
                val duration = i - m
                val elapsed = nowMillis - m
                CurrentPrayerStatus(
                    currentPrayer = PrayerType.MAGHRIB,
                    nextPrayer = PrayerType.ISHA,
                    nextPrayerTimeMillis = i,
                    remainingMillis = i - nowMillis,
                    progressFraction = (elapsed.toDouble() / duration.toDouble()).coerceIn(0.0, 1.0).toFloat(),
                    isPrayerTimeNow = true
                )
            }
            else -> {
                // After Isha, next is Fajr tomorrow
                val tomorrowFajr = f + 24 * 3600 * 1000L
                val duration = tomorrowFajr - i
                val elapsed = nowMillis - i
                CurrentPrayerStatus(
                    currentPrayer = PrayerType.ISHA,
                    nextPrayer = PrayerType.FAJR,
                    nextPrayerTimeMillis = tomorrowFajr,
                    remainingMillis = (tomorrowFajr - nowMillis).coerceAtLeast(0L),
                    progressFraction = (elapsed.toDouble() / duration.toDouble()).coerceIn(0.0, 1.0).toFloat(),
                    isPrayerTimeNow = true
                )
            }
        }
    }
}
