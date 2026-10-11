package com.example.data.calendar

import android.os.Build
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class BanglaDate(
    val day: Int,
    val monthIndex: Int, // 1 to 12
    val monthNameBangla: String,
    val yearBangla: Int,
    val formattedBangla: String,
    val seasonBangla: String,
    val fullDescriptionBangla: String = "$formattedBangla ($seasonBangla)"
)

data class EnglishDate(
    val day: Int,
    val monthIndex: Int, // 1 to 12
    val monthNameEnglish: String,
    val monthNameBangla: String,
    val year: Int,
    val dayOfWeekEnglish: String,
    val dayOfWeekBangla: String,
    val formattedEnglish: String,
    val formattedBangla: String,
    val fullDescriptionEnglish: String = "$formattedEnglish AD"
)

data class ArabicHijriDate(
    val day: Int,
    val monthIndex: Int, // 1 to 12
    val monthNameBangla: String,
    val monthNameArabic: String,
    val year: Int,
    val formattedBangla: String,
    val formattedArabic: String,
    val fullDescriptionBangla: String = "$formattedBangla ($formattedArabic)",
    val significanceBangla: String = ""
)

data class ThreeCalendarsDateInfo(
    val banglaDate: BanglaDate,
    val englishDate: EnglishDate,
    val hijriDate: ArabicHijriDate,
    val dayOfWeekBangla: String,
    val dayOfWeekArabic: String,
    val fullCombinedDateBangla: String = "$dayOfWeekBangla • ${banglaDate.formattedBangla} • ${englishDate.formattedBangla} • ${hijriDate.formattedBangla}"
)

object DateCalendarHelper {

    private val BANGLA_MONTHS = listOf(
        "বৈশাখ", "জ্যৈষ্ঠ", "আষাঢ়", "শ্রাবণ", "ভাদ্র", "আশ্বিন",
        "কার্তিক", "অগ্রহায়ণ", "পৌষ", "মাঘ", "ফাল্গুন", "চৈত্র"
    )

    private val BANGLA_SEASONS = listOf(
        "গ্রীষ্মকাল", "গ্রীষ্মকাল", "বর্ষাকাল", "বর্ষাকাল", "শরৎকাল", "শরৎকাল",
        "হেমন্তকাল", "হেমন্তকাল", "শীতকাল", "শীতকাল", "বসন্তকাল", "বসন্তকাল"
    )

    private val HIJRI_MONTHS_BANGLA = listOf(
        "মহররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি",
        "জমাদিউল আউয়াল", "জমাদিউস সানি", "রজব", "শাবান",
        "রমজান", "শাওয়াল", "জিলকদ", "জিলহজ"
    )

    private val HIJRI_MONTH_SIGNIFICANCE = listOf(
        "পবিত্র ও সম্মানিত মাস; ঐতিহাসিক আশুরার রোজা ও ত্যাগ-তিতিক্ষার স্মারক মাস।",
        "ইসলামের প্রাথমিক দাওয়াত ও ঐতিহাসিক ঘটনাবলির মাস; বেশি বেশি দোয়া ও ইবাদত কাম্য।",
        "বিশ্বনবী হযরত মুহাম্মদ (সাঃ)-এর ধরায় আগমন ও অনুপম জীবনাদর্শের পবিত্র মাস।",
        "সাহাবা ও বুজুর্গানে দ্বীনের ইলম চর্চা, নফল নামাজ ও জিকিরের মাস।",
        "ইসলামের ঐতিহাসিক মুতার যুদ্ধ ও দ্বীনের আলো বিস্তারের স্মারক মাস।",
        "খলিফা হযরত আবু বকর সিদ্দিক (রাঃ)-এর ওফাত ও আত্মশুদ্ধির মাস।",
        "সম্মানিত ৪টি মাসের অন্যতম এবং ঐতিহাসিক মিরাজের মহিমান্বিত মাস।",
        "রমজানুল মোবারকের প্রস্তুতি, অধিক নফল রোজা ও শবে বরাতের বরকতময় মাস।",
        "পবিত্র কুরআন নাজিলের মাস, ফরজ সিয়াম পালন ও লাইলাতুল কদরের শ্রেষ্ঠতম মাস।",
        "পবিত্র ঈদুল ফিতর ও শাওয়ালের বরকতময় ৬টি সুন্নাহ রোজার মাস।",
        "সম্মানিত চার মাসের একটি এবং পবিত্র হজের মাসসমূহের সূচনা।",
        "পবিত্র হজের মাস, ৯ই জিলহজ ঐতিহাসিক আরাফাত ও ঈদুল আজহার কুরবানির মহান মাস।"
    )

    private val HIJRI_MONTHS_ARABIC = listOf(
        "مُحَرَّم", "صَفَر", "رَبِيع الأَوَّل", "رَبِيع الثَّانِي",
        "جُمَادَى الأُولَى", "جُمَادَى الآخِرَة", "رَجَب", "شَعْبَان",
        "رَمَضَان", "شَوَّال", "ذُو القَعْدَة", "ذُو الحِجَّة"
    )

    private val ARABIC_WEEKDAYS = mapOf(
        Calendar.SATURDAY to "السَّبْت",
        Calendar.SUNDAY to "الأَحَد",
        Calendar.MONDAY to "الاِثْنَيْن",
        Calendar.TUESDAY to "الثُّلَاثَاء",
        Calendar.WEDNESDAY to "الأَرْبِعَاء",
        Calendar.THURSDAY to "الخَمِيس",
        Calendar.FRIDAY to "الجُمُعَة"
    )

    private val BANGLA_WEEKDAYS = mapOf(
        Calendar.SATURDAY to "শনিবার",
        Calendar.SUNDAY to "রবিবার",
        Calendar.MONDAY to "সোমবার",
        Calendar.TUESDAY to "মঙ্গলবার",
        Calendar.WEDNESDAY to "বুধবার",
        Calendar.THURSDAY to "বৃহস্পতিবার",
        Calendar.FRIDAY to "শুক্রবার"
    )

    private val ENGLISH_WEEKDAYS = mapOf(
        Calendar.SATURDAY to "Saturday",
        Calendar.SUNDAY to "Sunday",
        Calendar.MONDAY to "Monday",
        Calendar.TUESDAY to "Tuesday",
        Calendar.WEDNESDAY to "Wednesday",
        Calendar.THURSDAY to "Thursday",
        Calendar.FRIDAY to "Friday"
    )

    private val ENGLISH_MONTHS_BANGLA = listOf(
        "জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন",
        "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"
    )

    fun toBanglaDigit(number: Int): String {
        val banglaDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        return number.toString().map { ch ->
            if (ch in '0'..'9') banglaDigits[ch - '0'] else ch
        }.joinToString("")
    }

    fun toArabicDigit(number: Int): String {
        val arabicDigits = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
        return number.toString().map { ch ->
            if (ch in '0'..'9') arabicDigits[ch - '0'] else ch
        }.joinToString("")
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    /**
     * Converts a Gregorian date into Bangladesh Bangla Academy standard calendar date (বঙ্গাব্দ).
     * Pohela Boishakh is April 14.
     * First 6 months (Boishakh to Ashwin) = 31 days.
     * Kartik to Magh (4 months) = 30 days.
     * Falgun = 29 days (30 days in leap year).
     * Chaitra = 30 days.
     */
    fun getBanglaDate(calendar: Calendar): BanglaDate {
        val gYear = calendar.get(Calendar.YEAR)
        val gMonth = calendar.get(Calendar.MONTH) // 0-based: 0=Jan, 3=Apr
        val gDay = calendar.get(Calendar.DAY_OF_MONTH)

        // Determine start of Boishakh for the relevant Bengali year
        val april14Cal = Calendar.getInstance(calendar.timeZone).apply {
            set(gYear, Calendar.APRIL, 14, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val targetCal = Calendar.getInstance(calendar.timeZone).apply {
            set(gYear, gMonth, gDay, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val banglaYear: Int
        val startCal: Calendar
        val baseYearForLeap: Int

        if (!targetCal.before(april14Cal)) {
            // On or after April 14
            banglaYear = gYear - 593
            startCal = april14Cal
            baseYearForLeap = gYear + 1 // Falgun will occur next Gregorian year
        } else {
            // Before April 14
            banglaYear = gYear - 594
            startCal = Calendar.getInstance(calendar.timeZone).apply {
                set(gYear - 1, Calendar.APRIL, 14, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            baseYearForLeap = gYear // Falgun was/is in current Gregorian year
        }

        val falgunDays = if (isLeapYear(baseYearForLeap)) 30 else 29
        val monthLengths = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, falgunDays, 30)

        // Calculate days between startCal and targetCal
        val diffMillis = targetCal.timeInMillis - startCal.timeInMillis
        var daysDiff = (diffMillis / (1000L * 60 * 60 * 24)).toInt()

        var bMonthIndex = 0
        while (bMonthIndex < 12 && daysDiff >= monthLengths[bMonthIndex]) {
            daysDiff -= monthLengths[bMonthIndex]
            bMonthIndex++
        }

        val bDay = daysDiff + 1
        val safeMonthIndex = bMonthIndex.coerceIn(0, 11)
        val bMonthName = BANGLA_MONTHS[safeMonthIndex]
        val season = BANGLA_SEASONS[safeMonthIndex]
        val formatted = "${toBanglaDigit(bDay)} $bMonthName, ${toBanglaDigit(banglaYear)} বঙ্গাব্দ"

        return BanglaDate(
            day = bDay,
            monthIndex = safeMonthIndex + 1,
            monthNameBangla = bMonthName,
            yearBangla = banglaYear,
            formattedBangla = formatted,
            seasonBangla = season
        )
    }

    /**
     * Converts a Gregorian date to Hijri (Arabic) date using modern Java time HijrahDate
     * with astronomical Kuwaiti algorithm fallback.
     */
    fun getArabicHijriDate(calendar: Calendar, timeMillis: Long, timeZone: TimeZone, hijriOffsetDays: Int = 0): ArabicHijriDate {
        val adjustedMillis = timeMillis + (hijriOffsetDays * 86400000L)
        val adjustedCalendar = if (hijriOffsetDays != 0) {
            Calendar.getInstance(timeZone).apply {
                this.timeInMillis = adjustedMillis
            }
        } else {
            calendar
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val instant = java.time.Instant.ofEpochMilli(adjustedMillis)
                val zoneId = timeZone.toZoneId()
                val localDate = instant.atZone(zoneId).toLocalDate()
                val hijrahDate = java.time.chrono.HijrahDate.from(localDate)
                val hYear = hijrahDate.get(java.time.temporal.ChronoField.YEAR)
                val hMonth = hijrahDate.get(java.time.temporal.ChronoField.MONTH_OF_YEAR)
                val hDay = hijrahDate.get(java.time.temporal.ChronoField.DAY_OF_MONTH)

                val safeMonthIndex = (hMonth - 1).coerceIn(0, 11)
                val monthBangla = HIJRI_MONTHS_BANGLA[safeMonthIndex]
                val monthArabic = HIJRI_MONTHS_ARABIC[safeMonthIndex]
                val significance = HIJRI_MONTH_SIGNIFICANCE[safeMonthIndex]

                val formattedBangla = "${toBanglaDigit(hDay)} $monthBangla, ${toBanglaDigit(hYear)} হিজরি"
                val formattedArabic = "${toArabicDigit(hDay)} $monthArabic ${toArabicDigit(hYear)} هـ"

                return ArabicHijriDate(
                    day = hDay,
                    monthIndex = hMonth,
                    monthNameBangla = monthBangla,
                    monthNameArabic = monthArabic,
                    year = hYear,
                    formattedBangla = formattedBangla,
                    formattedArabic = formattedArabic,
                    significanceBangla = significance
                )
            } catch (_: Exception) {
                // Fallback to algorithmic conversion below
            }
        }

        return calculateHijriAlgorithmic(adjustedCalendar)
    }

    private fun calculateHijriAlgorithmic(calendar: Calendar): ArabicHijriDate {
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH) + 1
        val d = calendar.get(Calendar.DAY_OF_MONTH)

        var adjY = y
        var adjM = m
        if (adjM < 3) {
            adjY -= 1
            adjM += 12
        }

        val a = Math.floor(adjY / 100.0)
        val b = 2 - a + Math.floor(a / 4.0)
        val jd = Math.floor(365.25 * (adjY + 4716)) + Math.floor(30.6001 * (adjM + 1)) + d + b - 1524.5

        val l = (jd - 1948440 + 10632).toInt()
        val n = Math.floor((l - 1) / 10631.0).toInt()
        val l2 = l - 10631 * n + 354
        val j = (Math.floor((10985 - l2) / 5316.0) * Math.floor((50 * l2) / 17719.0) +
                Math.floor(l2 / 5670.0) * Math.floor((43 * l2) / 15238.0)).toInt()
        val l3 = (l2 - Math.floor((30 - j) / 15.0) * Math.floor((17719 * j) / 50.0) -
                Math.floor(j / 16.0) * Math.floor((15238 * j) / 43.0) + 29).toInt()
        val hMonth = Math.floor((24 * l3) / 709.0).toInt().coerceIn(1, 12)
        val hDay = (l3 - Math.floor((709 * hMonth) / 24.0)).toInt().coerceIn(1, 30)
        val hYear = 30 * n + j - 30

        val safeMonthIndex = (hMonth - 1).coerceIn(0, 11)
        val monthBangla = HIJRI_MONTHS_BANGLA[safeMonthIndex]
        val monthArabic = HIJRI_MONTHS_ARABIC[safeMonthIndex]
        val significance = HIJRI_MONTH_SIGNIFICANCE[safeMonthIndex]

        val formattedBangla = "${toBanglaDigit(hDay)} $monthBangla, ${toBanglaDigit(hYear)} হিজরি"
        val formattedArabic = "${toArabicDigit(hDay)} $monthArabic ${toArabicDigit(hYear)} هـ"

        return ArabicHijriDate(
            day = hDay,
            monthIndex = hMonth,
            monthNameBangla = monthBangla,
            monthNameArabic = monthArabic,
            year = hYear,
            formattedBangla = formattedBangla,
            formattedArabic = formattedArabic,
            significanceBangla = significance
        )
    }

    fun getEnglishDate(calendar: Calendar): EnglishDate {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthIndex = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val monthNameEnglish = SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.time)
        val monthNameBangla = ENGLISH_MONTHS_BANGLA[(monthIndex - 1).coerceIn(0, 11)]

        val dayOfWeekEnglish = ENGLISH_WEEKDAYS[dayOfWeek] ?: "Saturday"
        val dayOfWeekBangla = BANGLA_WEEKDAYS[dayOfWeek] ?: "শনিবার"

        val formattedEnglish = "$dayOfWeekEnglish, $day $monthNameEnglish $year"
        val formattedBangla = "$dayOfWeekBangla, ${toBanglaDigit(day)} $monthNameBangla ${toBanglaDigit(year)}"

        return EnglishDate(
            day = day,
            monthIndex = monthIndex,
            monthNameEnglish = monthNameEnglish,
            monthNameBangla = monthNameBangla,
            year = year,
            dayOfWeekEnglish = dayOfWeekEnglish,
            dayOfWeekBangla = dayOfWeekBangla,
            formattedEnglish = formattedEnglish,
            formattedBangla = formattedBangla
        )
    }

    fun getThreeCalendarDates(timeMillis: Long, timeZone: TimeZone, hijriOffsetDays: Int = 0): ThreeCalendarsDateInfo {
        val calendar = Calendar.getInstance(timeZone).apply {
            this.timeInMillis = timeMillis
        }

        val bangla = getBanglaDate(calendar)
        val english = getEnglishDate(calendar)
        val hijri = getArabicHijriDate(calendar, timeMillis, timeZone, hijriOffsetDays)

        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfWeekBangla = BANGLA_WEEKDAYS[dayOfWeek] ?: "শনিবার"
        val dayOfWeekArabic = ARABIC_WEEKDAYS[dayOfWeek] ?: "السَّبْت"

        return ThreeCalendarsDateInfo(
            banglaDate = bangla,
            englishDate = english,
            hijriDate = hijri,
            dayOfWeekBangla = dayOfWeekBangla,
            dayOfWeekArabic = dayOfWeekArabic
        )
    }
}
