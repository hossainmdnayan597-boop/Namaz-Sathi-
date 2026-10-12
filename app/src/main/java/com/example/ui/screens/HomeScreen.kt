package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.database.PrayerLog
import com.example.data.prayer.CurrentPrayerStatus
import com.example.data.prayer.DayPrayerSchedule
import com.example.data.prayer.PrayerTimeItem
import com.example.data.prayer.PrayerType
import com.example.data.preferences.UserSettings
import com.example.data.calendar.DateCalendarHelper
import com.example.ui.MainViewModel
import com.example.ui.components.AppFooter
import com.example.ui.components.ThreeCalendarDateCard
import com.example.ui.theme.AmiriArabicFontFamily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    userSettings: UserSettings,
    daySchedule: DayPrayerSchedule,
    prayerStatus: CurrentPrayerStatus,
    todayLog: PrayerLog?,
    currentTimeMillis: Long,
    onOpenCityPicker: () -> Unit,
    onOpenSettings: () -> Unit,
    onNavigateToTracker: () -> Unit,
    onNavigateToQuran: () -> Unit,
    onNavigateToHadith: () -> Unit,
    onNavigateToLearning: () -> Unit,
    onNavigateToQibla: () -> Unit,
    onNavigateToTasbih: () -> Unit,
    onNavigateToQuiz: () -> Unit = {}
) {
    val completedCount = todayLog?.completedCount ?: 0
    val progressPercent = (completedCount * 100) / 5
    val isAzanPlaying by com.example.notification.AzanAlarmSoundManager.isPlaying.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen_content"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // 1. Top Header Bar
        item {
            HomeTopBar(
                userSettings = userSettings,
                currentTimeMillis = currentTimeMillis,
                onOpenCityPicker = onOpenCityPicker,
                onOpenSettings = onOpenSettings
            )
        }

        // Active Azan Playing Alert Banner
        if (isAzanPlaying) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "📢", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "ওয়াক্তের আযান বাজছে",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "আসুন নামাজের দিকে • حي على الصلاة",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.stopAzanAudio() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Stop, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("থামান", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }
        }

        // 2. Hero Mosque Banner with Live Countdown
        item {
            HeroCountdownCard(
                prayerStatus = prayerStatus,
                schedule = daySchedule,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // 3. Three Calendars Date Card (বাংলা • ইংরেজি • আরবি হিজরি)
        item {
            ThreeCalendarDateCard(
                currentTimeMillis = currentTimeMillis,
                timeZoneId = userSettings.timeZoneId,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // 4. Quick Sehri & Iftar Row
        item {
            SehriIftarCard(
                schedule = daySchedule,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        // 5. Quick Features Grid (কুরআন, হাদিস, শিক্ষা, কিবলা, তাসবীহ, ট্র্যাকার, কুইজ)
        item {
            QuickIslamicServicesGrid(
                onNavigateToQuran = onNavigateToQuran,
                onNavigateToHadith = onNavigateToHadith,
                onNavigateToLearning = onNavigateToLearning,
                onNavigateToQibla = onNavigateToQibla,
                onNavigateToTasbih = onNavigateToTasbih,
                onNavigateToTracker = onNavigateToTracker,
                onNavigateToQuiz = onNavigateToQuiz,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // 6. Daily Prayer Times Header & Checklist Summary
        item {
            DailyPrayerSummaryCard(
                completedCount = completedCount,
                progressPercent = progressPercent,
                onNavigateToTracker = onNavigateToTracker,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // 7. Prayer Time Items
        val prayers = listOf(
            Triple(daySchedule.fajr, todayLog?.fajr ?: false, PrayerType.FAJR),
            Triple(daySchedule.sunrise, false, PrayerType.SUNRISE),
            Triple(daySchedule.dhuhr, todayLog?.dhuhr ?: false, PrayerType.DHUHR),
            Triple(daySchedule.asr, todayLog?.asr ?: false, PrayerType.ASR),
            Triple(daySchedule.maghrib, todayLog?.maghrib ?: false, PrayerType.MAGHRIB),
            Triple(daySchedule.isha, todayLog?.isha ?: false, PrayerType.ISHA)
        )

        items(prayers.size) { index ->
            val (item, isCompleted, type) = prayers[index]
            val isCurrent = prayerStatus.currentPrayer == type && prayerStatus.isPrayerTimeNow

            PrayerTimeRowItem(
                item = item,
                isCompleted = isCompleted,
                isCurrent = isCurrent,
                isCheckable = type != PrayerType.SUNRISE,
                onToggle = {
                    viewModel.togglePrayerForDate(daySchedule.dateString, type)
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        // 8. Islamic Reflection / Hadith of the Day
        item {
            DailyAyahCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // 9. App Footer (Developed by Md. Nayan Hossain with Facebook link)
        item {
            AppFooter(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
private fun QuickIslamicServicesGrid(
    onNavigateToQuran: () -> Unit,
    onNavigateToHadith: () -> Unit,
    onNavigateToLearning: () -> Unit,
    onNavigateToQibla: () -> Unit,
    onNavigateToTasbih: () -> Unit,
    onNavigateToTracker: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "ইসলামিক দ্বীনি সেবা ও সহায়কসমূহ",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Row 1: Quran, Hadith, Learning
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickServiceItem(
                    title = "পবিত্র কুরআন",
                    subtitle = "১১৪ সূরা ও উচ্চারণ",
                    icon = Icons.Default.MenuBook,
                    iconTint = MaterialTheme.colorScheme.primary,
                    bgColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = onNavigateToQuran,
                    modifier = Modifier.weight(1f),
                    testTag = "home_quick_quran"
                )
                QuickServiceItem(
                    title = "সহিহ হাদিস",
                    subtitle = "বুখারী ও মুসলিম",
                    icon = Icons.Default.AutoStories,
                    iconTint = Color(0xFFD97706),
                    bgColor = Color(0xFFFEF3C7),
                    onClick = onNavigateToHadith,
                    modifier = Modifier.weight(1f),
                    testTag = "home_quick_hadith"
                )
                QuickServiceItem(
                    title = "নামাজ শিক্ষা",
                    subtitle = "ওজু, দু'আ ও নিয়ম",
                    icon = Icons.Default.School,
                    iconTint = Color(0xFF0D9488),
                    bgColor = Color(0xFFCCFBF1),
                    onClick = onNavigateToLearning,
                    modifier = Modifier.weight(1f),
                    testTag = "home_quick_learning"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 2: Qibla, Tasbih, Tracker
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickServiceItem(
                    title = "কিবলা কম্পাস",
                    subtitle = "মক্কার দিকদর্শন",
                    icon = Icons.Default.Explore,
                    iconTint = Color(0xFFEA580C),
                    bgColor = Color(0xFFFFEDD5),
                    onClick = onNavigateToQibla,
                    modifier = Modifier.weight(1f),
                    testTag = "home_quick_qibla"
                )
                QuickServiceItem(
                    title = "ডিজিটাল তাসবীহ",
                    subtitle = "মাসনূন জিকির",
                    icon = Icons.Default.Fingerprint,
                    iconTint = Color(0xFF7C3AED),
                    bgColor = Color(0xFFEDE9FE),
                    onClick = onNavigateToTasbih,
                    modifier = Modifier.weight(1f),
                    testTag = "home_quick_tasbih"
                )
                QuickServiceItem(
                    title = "নামাজ ট্র্যাকার",
                    subtitle = "আমলনামা ট্র্যাকিং",
                    icon = Icons.Default.CheckCircle,
                    iconTint = Color(0xFF059669),
                    bgColor = Color(0xFFD1FAE5),
                    onClick = onNavigateToTracker,
                    modifier = Modifier.weight(1f),
                    testTag = "home_quick_tracker"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Row 3: Islamic Quiz Special Banner
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFFEF3C7),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToQuiz() }
                    .testTag("home_quick_quiz_banner")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "🕌", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "ইসলামিক কুইজ ও প্রশ্নভাণ্ডার",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Color(0xFFD97706)
                                ) {
                                    Text(
                                        text = "নতুন",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = "কুরআন, হাদিস ও নবীগণের জীবনী নিয়ে কুইজ খেলুন",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFB45309)
                            )
                        }
                    }

                    FilledTonalButton(
                        onClick = onNavigateToQuiz,
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = Color(0xFFD97706),
                            contentColor = Color.White
                        )
                    ) {
                        Text("কুইজ খেলুন", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickServiceItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    bgColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .testTag(testTag),
        color = bgColor.copy(alpha = 0.45f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun HomeTopBar(
    userSettings: UserSettings,
    currentTimeMillis: Long,
    onOpenCityPicker: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val timeZone = remember(userSettings.timeZoneId) {
        java.util.TimeZone.getTimeZone(userSettings.timeZoneId)
    }

    val threeDates = remember(userSettings.timeZoneId, currentTimeMillis / 60000) {
        DateCalendarHelper.getThreeCalendarDates(currentTimeMillis, timeZone)
    }

    val liveClockBengali = remember(currentTimeMillis, userSettings.timeZoneId) {
        com.example.data.prayer.PrayerCalculator.formatBangla(currentTimeMillis, timeZone)
    }

    val isBangladesh = userSettings.timeZoneId == "Asia/Dhaka"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "নামাজ সাথী",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.clickable { onOpenCityPicker() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "শহর নির্বাচন",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = userSettings.cityName,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Text(
                    text = "${threeDates.dayOfWeekBangla} • ${DateCalendarHelper.toBanglaDigit(threeDates.banglaDate.day)} ${threeDates.banglaDate.monthNameBangla} • ${DateCalendarHelper.toBanglaDigit(threeDates.hijriDate.day)} ${threeDates.hijriDate.monthNameBangla} • ${threeDates.englishDate.day} ${threeDates.englishDate.monthNameBangla}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = " • ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = liveClockBengali,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = if (isBangladesh) "বাংলাদেশ সময় (BST, UTC+6:00)" else "${userSettings.timeZoneId} (স্থানীয় সময়)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.SemiBold
            )
        }

        IconButton(
            onClick = onOpenSettings,
            modifier = Modifier.testTag("settings_top_button")
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "সেটিংস",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun HeroCountdownCard(
    prayerStatus: CurrentPrayerStatus,
    schedule: DayPrayerSchedule,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp)
            .testTag("hero_countdown_card"),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.img_mosque_banner),
                contentDescription = "মসজিদ দৃশ্য",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Dark gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x66000000),
                                Color(0xCC06251A),
                                Color(0xF0031811)
                            )
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0x33FFFFFF),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (prayerStatus.isPrayerTimeNow)
                                "এখন ${prayerStatus.currentPrayer.banglaName} নামাজের ওয়াক্ত"
                            else
                                "পরবর্তী ওয়াক্ত: ${prayerStatus.nextPrayer.banglaName}",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = prayerStatus.nextPrayer.arabicName,
                        color = Color(0xFFFDE68A),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Center Countdown
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val remSeconds = (prayerStatus.remainingMillis / 1000).coerceAtLeast(0)
                    val hours = remSeconds / 3600
                    val minutes = (remSeconds % 3600) / 60
                    val seconds = remSeconds % 60
                    val countdownStr = String.format("%02d:%02d:%02d", hours, minutes, seconds)

                    Text(
                        text = "${prayerStatus.nextPrayer.banglaName} শুরু হতে বাকি",
                        color = Color(0xFFE2E8F0),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = countdownStr,
                        color = Color(0xFFFDE68A),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                }

                // Progress Bar
                Column(modifier = Modifier.fillMaxWidth()) {
                    LinearProgressIndicator(
                        progress = { prayerStatus.progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = Color(0xFF34D399),
                        trackColor = Color(0x44FFFFFF),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "বর্তমান: ${prayerStatus.currentPrayer.banglaName}",
                            color = Color(0xFFCBD5E1),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "পরবর্তী: ${prayerStatus.nextPrayer.banglaName}",
                            color = Color(0xFFCBD5E1),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SehriIftarCard(
    schedule: DayPrayerSchedule,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.NightsStay,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "সেহরি শেষ (তাহাজ্জুদ)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = schedule.sehriEndTime.formattedTime12h,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.WbTwilight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "ইফতার ও মাগরিব",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = schedule.iftarTime.formattedTime12h,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyPrayerSummaryCard(
    completedCount: Int,
    progressPercent: Int,
    onNavigateToTracker: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigateToTracker() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "আজকের সালাত ট্র্যাকার",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "$completedCount / ৫ ফরজ",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { completedCount / 5f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (completedCount == 5) "আলহামদুলিল্লাহ! সকল ফরজ সালাত আদায় হয়েছে" else "ওয়াক্ত শেষ হওয়ার আগেই সালাত আদায় করে নিন",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "বিস্তারিত >",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun PrayerTimeRowItem(
    item: PrayerTimeItem,
    isCompleted: Boolean,
    isCurrent: Boolean,
    isCheckable: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            isCurrent -> MaterialTheme.colorScheme.primaryContainer
            isCompleted -> MaterialTheme.colorScheme.surface
            else -> MaterialTheme.colorScheme.surface
        },
        label = "prayerRowBg"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("prayer_row_${item.type.name}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrent) 2.dp else 0.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox for tracking
            if (isCheckable) {
                FilledIconToggleButton(
                    checked = isCompleted,
                    onCheckedChange = { onToggle() },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("toggle_prayer_${item.type.name}"),
                    colors = IconButtonDefaults.filledIconToggleButtonColors(
                        checkedContainerColor = MaterialTheme.colorScheme.primary,
                        checkedContentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    if (isCompleted) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "আদায়কৃত", modifier = Modifier.size(20.dp))
                    } else {
                        Icon(imageVector = Icons.Outlined.Circle, contentDescription = "বাকি আছে", modifier = Modifier.size(16.dp))
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Prayer Name & Arabic
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.type.banglaName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.SemiBold,
                        color = if (isCurrent) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                    )
                    if (isCurrent) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            Text("বর্তমান", modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                        }
                    }
                }
                Text(
                    text = item.type.rakatsSummary,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Arabic text & Time
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = item.formattedTime12h,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.type.arabicName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun DailyAyahCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "প্রতিদিনের অনুপ্রেরণা",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "إِنَّ الصَّلَاةَ كَانَتْ عَلَى الْمُؤْمِنِينَ كِتَابًا مَوْقُوتًا",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = AmiriArabicFontFamily,
                    fontSize = 20.sp,
                    lineHeight = 34.sp
                ),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "\"নিশ্চয়ই সালাত মুমিনদের ওপর নির্দিষ্ট সময়ে ফরজ করা হয়েছে।\"",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "— সূরা আন-নিসা: ১০৩",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
