package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.database.PrayerLog
import com.example.data.prayer.PrayerType
import com.example.ui.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun TrackerScreen(
    viewModel: MainViewModel,
    selectedDateStr: String,
    todayLog: PrayerLog?,
    recentLogs: List<PrayerLog>
) {
    val coroutineScope = rememberCoroutineScope()
    var notesText by remember(todayLog?.notes) { mutableStateOf(todayLog?.notes ?: "") }
    var isSavingNotes by remember { mutableStateOf(false) }

    val completedCount = todayLog?.completedCount ?: 0
    val isAllFard = completedCount == 5

    // Streak calculation from recent logs
    val streakDays = remember(recentLogs) {
        var streak = 0
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val todayCalendar = Calendar.getInstance()

        // Check consecutive days starting from today or yesterday
        for (i in 0 until 60) {
            val dateKey = sdf.format(todayCalendar.time)
            val log = recentLogs.find { it.date == dateKey }
            if (log != null && log.isAllFardCompleted) {
                streak++
            } else if (i > 0) {
                // If broken before today, break streak
                break
            }
            todayCalendar.add(Calendar.DAY_OF_YEAR, -1)
        }
        streak
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("tracker_screen_content"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Header Title
        item {
            Column {
                Text(
                    text = "সালাত ট্র্যাকার",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "দৈনিক ৫ ওয়াক্ত নামাজের হিসাব রাখুন ও অভ্যাসে পরিণত করুন",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 2. Date Navigation Bar
        item {
            DateNavigationCard(
                dateStr = selectedDateStr,
                onPreviousDay = { viewModel.changeDateOffset(-1) },
                onNextDay = { viewModel.changeDateOffset(1) },
                onToday = { viewModel.goToToday() }
            )
        }

        // 3. Daily Completion Ring / Progress Card
        item {
            DailyProgressCard(
                completedCount = completedCount,
                isAllFard = isAllFard,
                hasTahajjud = todayLog?.tahajjud ?: false
            )
        }

        // 4. Prayer Checklist Header
        item {
            Text(
                text = "আজকের ওয়াক্তসমূহ",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // 5. Checklist Items
        val prayerItems = listOf(
            Triple(PrayerType.FAJR, todayLog?.fajr ?: false, "২ রাকাত সুন্নত, ২ রাকাত ফরজ"),
            Triple(PrayerType.DHUHR, todayLog?.dhuhr ?: false, "৪ সুন্নত, ৪ ফরজ, ২ সুন্নত, ২ নফল"),
            Triple(PrayerType.ASR, todayLog?.asr ?: false, "৪ সুন্নতে গায়রে মুয়াক্কাদা, ৪ ফরজ"),
            Triple(PrayerType.MAGHRIB, todayLog?.maghrib ?: false, "৩ ফরজ, ২ সুন্নত, ২ নফল"),
            Triple(PrayerType.ISHA, todayLog?.isha ?: false, "৪ ফরজ, ২ সুন্নত, ২ নফল, ৩ বিতর"),
            Triple(PrayerType.TAHAJJUD, todayLog?.tahajjud ?: false, "শেষ রাতের বিশেষ নফল ইবাদত")
        )

        items(prayerItems.size) { index ->
            val (type, isChecked, subtitle) = prayerItems[index]
            TrackerCheckItem(
                type = type,
                isChecked = isChecked,
                subtitle = subtitle,
                onToggle = {
                    viewModel.togglePrayerForDate(selectedDateStr, type)
                }
            )
        }

        // 6. Streak & Monthly Statistics
        item {
            StreakAndStatsCard(
                streakDays = streakDays,
                recentLogs = recentLogs
            )
        }

        // 7. Personal Notes & Reflections
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ব্যক্তিগত নোট বা অনুভূতি",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        if (notesText != (todayLog?.notes ?: "")) {
                            FilledTonalButton(
                                onClick = {
                                    coroutineScope.launch {
                                        isSavingNotes = true
                                        viewModel.prayerRepository.savePrayerNotes(selectedDateStr, notesText)
                                        isSavingNotes = false
                                        viewModel.vibrate(40)
                                    }
                                },
                                modifier = Modifier.testTag("save_notes_button")
                            ) {
                                Text(if (isSavingNotes) "সংরক্ষণ হচ্ছে..." else "সংরক্ষণ করুন")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("prayer_notes_input"),
                        placeholder = { Text("যেমন: আজ যোহর ও মাগরিব জামাআতে পড়েছি...") },
                        minLines = 2,
                        maxLines = 4
                    )
                }
            }
        }
    }
}

@Composable
private fun DateNavigationCard(
    dateStr: String,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onToday: () -> Unit
) {
    val formattedDate = remember(dateStr) {
        try {
            val tz = java.util.TimeZone.getTimeZone("Asia/Dhaka")
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).apply { timeZone = tz }
            val d = sdf.parse(dateStr) ?: Date()
            val out = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("bn", "BD")).apply { timeZone = tz }
            out.format(d)
        } catch (e: Exception) {
            dateStr
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPreviousDay,
                modifier = Modifier.testTag("prev_day_button")
            ) {
                Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "পূর্ববর্তী দিন")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onToday() }
            ) {
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "আজকের দিনে যেতে ট্যাপ করুন",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = onNextDay,
                modifier = Modifier.testTag("next_day_button")
            ) {
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "পরবর্তী দিন")
            }
        }
    }
}

@Composable
private fun DailyProgressCard(
    completedCount: Int,
    isAllFard: Boolean,
    hasTahajjud: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isAllFard) Color(0xFFD1FAE5) else MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circle progress badge
            Surface(
                shape = CircleShape,
                color = if (isAllFard) Color(0xFF10B981) else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$completedCount/৫",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "ফরজ",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFE2E8F0)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isAllFard) "মাশাআল্লাহ! ৫ ওয়াক্ত সম্পন্ন" else "আজকের সালাত সম্পূর্ণ করুন",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isAllFard) Color(0xFF064E3B) else MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = when {
                        isAllFard && hasTahajjud -> "৫ ফরজ এবং তাহাজ্জুদ সালাত সম্পন্ন হয়েছে। আল্লাহ কবুল করুন!"
                        isAllFard -> "সকল ফরজ সালাত আদায় হয়েছে। তাহাজ্জুদ সালাতেরও যত্ন নিন।"
                        completedCount > 0 -> "বাকি ওয়াক্তগুলো সঠিক সময়ে আদায় করার চেষ্টা করুন।"
                        else -> "ওয়াক্ত অনুযায়ী সালাত আদায় করে চেকমার্ক দিন।"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isAllFard) Color(0xFF064E3B) else MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
private fun TrackerCheckItem(
    type: PrayerType,
    isChecked: Boolean,
    subtitle: String,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("tracker_item_${type.name}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconToggleButton(
                checked = isChecked,
                onCheckedChange = { onToggle() },
                modifier = Modifier.size(36.dp),
                colors = IconButtonDefaults.filledIconToggleButtonColors(
                    checkedContainerColor = MaterialTheme.colorScheme.primary,
                    checkedContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                if (isChecked) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "সম্পন্ন", modifier = Modifier.size(20.dp))
                } else {
                    Icon(imageVector = Icons.Outlined.Circle, contentDescription = "বাকি", modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = type.banglaName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = type.arabicName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StreakAndStatsCard(
    streakDays: Int,
    recentLogs: List<PrayerLog>
) {
    val totalPrayersThisMonth = recentLogs.sumOf { it.completedCount }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "সালাতের অগ্রগতি ও ধারাবাহিকতা",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Streak Card
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = Color(0xFFEA580C),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$streakDays দিন",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEA580C)
                        )
                        Text(
                            text = "ধারাবাহিক ৫ ওয়াক্ত",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Total Prayers Card
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$totalPrayersThisMonth ওয়াক্ত",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "সাম্প্রতিক আদায়কৃত",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
