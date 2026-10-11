package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.calendar.DateCalendarHelper
import com.example.data.calendar.ThreeCalendarsDateInfo
import java.util.TimeZone

@Composable
fun ThreeCalendarDateCard(
    currentTimeMillis: Long,
    timeZoneId: String,
    modifier: Modifier = Modifier
) {
    val timeZone = remember(timeZoneId) {
        TimeZone.getTimeZone(timeZoneId)
    }

    var hijriOffsetDays by remember { mutableStateOf(0) }
    var showFullDialog by remember { mutableStateOf(false) }
    var selectedCalendarType by remember { mutableStateOf(0) } // 0=Summary, 1=Bangla, 2=English, 3=Hijri

    val threeDates = remember(currentTimeMillis / 60000, timeZoneId, hijriOffsetDays) {
        DateCalendarHelper.getThreeCalendarDates(currentTimeMillis, timeZone, hijriOffsetDays)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("three_calendar_date_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Title & Day of week badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "আজকের পূর্ণাঙ্গ তারিখ (৩টি ক্যালেন্ডার)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "বাংলা • ইংরেজি • আরবি (হিজরি সন)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                ) {
                    Text(
                        text = threeDates.dayOfWeekBangla,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Three Date Pillars
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 1. Bangla Date Card
                DatePillarItem(
                    flagOrIcon = "🇧🇩",
                    title = "বাংলা পঞ্জিকা",
                    primaryText = "${DateCalendarHelper.toBanglaDigit(threeDates.banglaDate.day)} ${threeDates.banglaDate.monthNameBangla}",
                    yearText = "${DateCalendarHelper.toBanglaDigit(threeDates.banglaDate.yearBangla)} বঙ্গাব্দ",
                    subBadge = threeDates.banglaDate.seasonBangla,
                    accentColor = Color(0xFF047857), // Forest Green
                    bgContainer = Color(0xFFD1FAE5),
                    isSelected = selectedCalendarType == 1,
                    onClick = {
                        selectedCalendarType = if (selectedCalendarType == 1) 0 else 1
                    },
                    modifier = Modifier.weight(1f),
                    testTag = "date_pillar_bangla"
                )

                // 2. English Date Card
                DatePillarItem(
                    flagOrIcon = "🇬🇧",
                    title = "ইংরেজি তারিখ",
                    primaryText = "${threeDates.englishDate.day} ${threeDates.englishDate.monthNameEnglish.take(3)}",
                    yearText = "${threeDates.englishDate.year} AD",
                    subBadge = threeDates.englishDate.dayOfWeekEnglish.take(3),
                    accentColor = Color(0xFF1D4ED8), // Royal Blue
                    bgContainer = Color(0xFFDBEAFE),
                    isSelected = selectedCalendarType == 2,
                    onClick = {
                        selectedCalendarType = if (selectedCalendarType == 2) 0 else 2
                    },
                    modifier = Modifier.weight(1f),
                    testTag = "date_pillar_english"
                )

                // 3. Arabic Hijri Date Card
                DatePillarItem(
                    flagOrIcon = "🌙",
                    title = "হিজরি সন",
                    primaryText = "${DateCalendarHelper.toBanglaDigit(threeDates.hijriDate.day)} ${threeDates.hijriDate.monthNameBangla}",
                    yearText = "${DateCalendarHelper.toBanglaDigit(threeDates.hijriDate.year)} হিজরি",
                    subBadge = threeDates.hijriDate.monthNameArabic,
                    accentColor = Color(0xFFB45309), // Amber / Gold
                    bgContainer = Color(0xFFFEF3C7),
                    isSelected = selectedCalendarType == 3,
                    onClick = {
                        selectedCalendarType = if (selectedCalendarType == 3) 0 else 3
                    },
                    modifier = Modifier.weight(1f),
                    testTag = "date_pillar_hijri"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Expandable Selected Calendar Deep View
            AnimatedVisibility(visible = selectedCalendarType != 0) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        when (selectedCalendarType) {
                            1 -> {
                                Text(
                                    text = "🇧🇩 বাংলা পঞ্জিকা বিবরণ",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF047857)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "পূর্ণ তারিখ: ${threeDates.banglaDate.formattedBangla}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "ঋতু: ${threeDates.banglaDate.seasonBangla} • প্রমিত বাংলা একাডেমি ক্যালেন্ডার অনুসারে",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            2 -> {
                                Text(
                                    text = "🇬🇧 Gregorian (ইংরেজি) ক্যালেন্ডার",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1D4ED8)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Full Date: ${threeDates.englishDate.formattedEnglish}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "বাংলায়: ${threeDates.englishDate.formattedBangla}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            3 -> {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "🌙 হিজরি আরবি পঞ্জিকা",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB45309)
                                    )
                                    // Offset adjustments
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "সমন্বয়: ",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        FilledTonalButton(
                                            onClick = { hijriOffsetDays-- },
                                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.height(26.dp)
                                        ) {
                                            Text("-১ দিন", fontSize = 10.sp)
                                        }
                                        Spacer(modifier = Modifier.width(4.dp))
                                        FilledTonalButton(
                                            onClick = { hijriOffsetDays++ },
                                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.height(26.dp)
                                        ) {
                                            Text("+১ দিন", fontSize = 10.sp)
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "পূর্ণ তারিখ: ${threeDates.hijriDate.formattedBangla} (${threeDates.hijriDate.formattedArabic})",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                if (threeDates.hijriDate.significanceBangla.isNotBlank()) {
                                    Text(
                                        text = "তাৎপর্য: ${threeDates.hijriDate.significanceBangla}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Detailed Full View banner
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "বাংলা: ${threeDates.banglaDate.formattedBangla}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "(${threeDates.banglaDate.seasonBangla})",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF047857),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ইংরেজি: ${threeDates.englishDate.formattedBangla}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = threeDates.englishDate.formattedEnglish,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF1D4ED8)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "আরবি: ${threeDates.hijriDate.formattedBangla}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFB45309)
                        )
                        Text(
                            text = threeDates.hijriDate.formattedArabic,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action button to open Full Calendar Dialog
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { showFullDialog = true },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.testTag("open_full_calendar_dialog")
                ) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "সম্পূর্ণ ক্যালেন্ডার ও হিজরি সমন্বয়",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Full 3-Calendar Dialog
    if (showFullDialog) {
        FullCalendarDetailsDialog(
            threeDates = threeDates,
            hijriOffsetDays = hijriOffsetDays,
            onAdjustHijri = { delta -> hijriOffsetDays += delta },
            onDismiss = { showFullDialog = false }
        )
    }
}

@Composable
private fun DatePillarItem(
    flagOrIcon: String,
    title: String,
    primaryText: String,
    yearText: String,
    subBadge: String,
    accentColor: Color,
    bgContainer: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) bgContainer else bgContainer.copy(alpha = 0.5f),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, accentColor) else null
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = flagOrIcon, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    fontWeight = FontWeight.SemiBold,
                    color = accentColor,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = primaryText,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Text(
                text = yearText,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = accentColor.copy(alpha = 0.15f)
            ) {
                Text(
                    text = subBadge,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                    fontWeight = FontWeight.Medium,
                    color = accentColor,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun FullCalendarDetailsDialog(
    threeDates: ThreeCalendarsDateInfo,
    hijriOffsetDays: Int,
    onAdjustHijri: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("full_calendar_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "সম্পূর্ণ ক্যালেন্ডার বিবরণ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "বাংলা, ইংরেজি ও হিজরি পূর্ণাঙ্গ তারিখ",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "বন্ধ করুন")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 1. Bangla Full Section
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFD1FAE5),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🇧🇩", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "বাংলা পঞ্জিকা (বঙ্গাব্দ)",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF047857),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${threeDates.dayOfWeekBangla}, ${threeDates.banglaDate.formattedBangla}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF065F46)
                        )
                        Text(
                            text = "ঋতু: ${threeDates.banglaDate.seasonBangla} • বাংলা বর্ষপঞ্জি",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF047857)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 2. English Full Section
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFDBEAFE),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🇬🇧", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Gregorian Calendar (ইংরেজি)",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1D4ED8),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = threeDates.englishDate.formattedEnglish,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E40AF)
                        )
                        Text(
                            text = "বাংলায়: ${threeDates.englishDate.formattedBangla}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF1D4ED8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 3. Hijri Full Section with Adjustment
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFEF3C7),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🌙", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "পবিত্র হিজরি সন (ইসলামিক)",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB45309),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                FilledTonalButton(
                                    onClick = { onAdjustHijri(-1) },
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                                    modifier = Modifier.height(26.dp)
                                ) {
                                    Text("-১", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                FilledTonalButton(
                                    onClick = { onAdjustHijri(1) },
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                                    modifier = Modifier.height(26.dp)
                                ) {
                                    Text("+১", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${threeDates.hijriDate.formattedBangla} (${threeDates.hijriDate.formattedArabic})",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF92400E)
                        )
                        if (hijriOffsetDays != 0) {
                            Text(
                                text = "হিজরি সমন্বয়: ${if (hijriOffsetDays > 0) "+$hijriOffsetDays" else "$hijriOffsetDays"} দিন",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFB45309),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (threeDates.hijriDate.significanceBangla.isNotBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "তাৎপর্য: ${threeDates.hijriDate.significanceBangla}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF78350F)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("ঠিক আছে", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
