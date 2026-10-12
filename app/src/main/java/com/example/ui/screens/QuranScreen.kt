package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.quran.QuranData
import com.example.data.quran.QuranSurah
import com.example.ui.components.AppFooter
import com.example.ui.theme.AmiriArabicFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") }
    var selectedSurah by remember { mutableStateOf<QuranSurah?>(null) }

    val filteredSurahs = remember(searchQuery, selectedFilter) {
        QuranData.searchSurahs(searchQuery, selectedFilter)
    }

    if (selectedSurah != null) {
        val fullSurah = remember(selectedSurah) {
            QuranData.getFullSurah(selectedSurah!!.number)
        }
        SurahDetailView(
            surah = fullSurah,
            onBack = { selectedSurah = null }
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .testTag("quran_screen_content"),
            contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            item {
                Column {
                    Text(
                        text = "পবিত্র আল-কুরআন",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "১১৪ সূরার সম্পূর্ণ তালিকা, বিশুদ্ধ বাংলা উচ্চারণ ও প্রাঞ্জল অর্থ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quran_search_input"),
                    placeholder = { Text("সূরার নাম, অর্থ, আরবি বা নম্বর লিখুন...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "অনুসন্ধান")
                    },
                    trailingIcon = if (searchQuery.isNotEmpty()) {
                        {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "মুছুন")
                            }
                        }
                    } else null,
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )
            }

            // Filter Chips
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filterOptions = listOf(
                        Pair("ALL", "সকল ১১৪ সূরা"),
                        Pair("JUZ_30", "৩০তম পারা (আমপারা)"),
                        Pair("VIRTUES", "ফজিলতপূর্ণ সূরা"),
                        Pair("MAKKI", "মাক্কী সূরা"),
                        Pair("MADANI", "মাদানী সূরা")
                    )

                    items(filterOptions) { (key, label) ->
                        FilterChip(
                            selected = selectedFilter == key,
                            onClick = { selectedFilter = key },
                            label = { Text(label) },
                            modifier = Modifier.testTag("quran_chip_$key")
                        )
                    }
                }
            }

            // Stats row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "মোট সূরা: ${filteredSurahs.size} টি",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "৩০তম পারার ৩৭টি সূরার পূর্ণ উচ্চারণ অন্তর্ভুক্ত",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // Surah Cards List
            if (filteredSurahs.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "কোনো সূরা পাওয়া যায়নি",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(filteredSurahs, key = { it.number }) { surah ->
                    SurahListItemCard(
                        surah = surah,
                        onClick = {
                            selectedSurah = QuranData.getFullSurah(surah.number)
                        }
                    )
                }
            }

            item {
                AppFooter(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp))
            }
        }
    }
}

@Composable
private fun SurahListItemCard(
    surah: QuranSurah,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("surah_card_${surah.number}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Surah Number in circular badge
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${surah.number}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Surah Names & Meta
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = surah.nameBangla,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "(${surah.nameEnglish})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "অর্থ: ${surah.meaningBangla}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )

                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (surah.revelationType == "মাক্কী")
                            MaterialTheme.colorScheme.secondaryContainer
                        else MaterialTheme.colorScheme.tertiaryContainer
                    ) {
                        Text(
                            text = surah.revelationType,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "${surah.totalVerses} আয়াত",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = surah.juzNumber,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Arabic calligraphy name
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = surah.nameArabic,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = AmiriArabicFontFamily,
                        fontSize = 22.sp
                    ),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                if (surah.virtuesBangla != null) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "ফজিলতপূর্ণ",
                        tint = Color(0xFFD97706),
                        modifier = Modifier
                            .size(16.dp)
                            .padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurahDetailView(
    surah: QuranSurah,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var showArabic by remember { mutableStateOf(true) }
    var showTransliteration by remember { mutableStateOf(true) }
    var showTranslation by remember { mutableStateOf(true) }
    var fontSizeMultiplier by remember { mutableFloatStateOf(1.0f) }

    BackHandler { onBack() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "সূরা ${surah.nameBangla}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${surah.nameArabic} • ${surah.revelationType} • ${surah.totalVerses} আয়াত",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরে যান"
                        )
                    }
                },
                actions = {
                    // Font size decrease
                    IconButton(
                        onClick = {
                            if (fontSizeMultiplier > 0.8f) fontSizeMultiplier -= 0.1f
                        }
                    ) {
                        Icon(imageVector = Icons.Default.FormatSize, contentDescription = "ফন্ট ছোট")
                    }
                    // Font size increase
                    IconButton(
                        onClick = {
                            if (fontSizeMultiplier < 1.4f) fontSizeMultiplier += 0.1f
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ZoomIn, contentDescription = "ফন্ট বড়")
                    }
                    // Share
                    IconButton(
                        onClick = {
                            val shareText = buildString {
                                append("সূরা ${surah.nameBangla} (${surah.nameArabic})\n")
                                append("অর্থ: ${surah.meaningBangla} • ${surah.totalVerses} আয়াত\n\n")
                                if (surah.arabicText != null) {
                                    append("আরবি:\n${surah.arabicText}\n\n")
                                }
                                if (surah.transliterationBangla != null) {
                                    append("বাংলা উচ্চারণ:\n${surah.transliterationBangla}\n\n")
                                }
                                if (surah.translationBangla != null) {
                                    append("বাংলা অর্থ:\n${surah.translationBangla}\n\n")
                                }
                                append("— নামাজ সাথী অ্যাপ")
                            }
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(intent, "শেয়ার করুন"))
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "শেয়ার")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Surah Top Info Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = surah.nameArabic,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontFamily = AmiriArabicFontFamily,
                                fontSize = 32.sp
                            ),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "সূরা ${surah.nameBangla} (${surah.nameEnglish})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "অর্থ: ${surah.meaningBangla}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${surah.revelationType} • আয়াত সংখ্যা: ${surah.totalVerses} • ${surah.juzNumber}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Display Control Toggles
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = showArabic,
                        onClick = { showArabic = !showArabic },
                        label = { Text("আরবি") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = showTransliteration,
                        onClick = { showTransliteration = !showTransliteration },
                        label = { Text("বাংলা উচ্চারণ") },
                        modifier = Modifier.weight(1.3f)
                    )
                    FilterChip(
                        selected = showTranslation,
                        onClick = { showTranslation = !showTranslation },
                        label = { Text("বাংলা অর্থ") },
                        modifier = Modifier.weight(1.1f)
                    )
                }
            }

            // Virtues / Fazilat Banner if available
            if (!surah.virtuesBangla.isNullOrBlank()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "সূরার বিশেষ ফজিলত ও তাৎপর্য:",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = surah.virtuesBangla,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
            }

            // Bismillah Header (except Surah 9 At-Tawbah)
            if (surah.number != 9) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontFamily = AmiriArabicFontFamily,
                                fontSize = (24 * fontSizeMultiplier).sp,
                                lineHeight = (42 * fontSizeMultiplier).sp
                            ),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Verses Content Cards
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Arabic Section
                        if (showArabic && !surah.arabicText.isNullOrBlank()) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "পবিত্র মূল আরবি টেক্সট",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    IconButton(
                                        onClick = {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = ClipData.newPlainText("আরবি টেক্সট", surah.arabicText)
                                            clipboard.setPrimaryClip(clip)
                                            Toast.makeText(context, "আরবি কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "কপি",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                ) {
                                    Text(
                                        text = surah.arabicText,
                                        modifier = Modifier.padding(14.dp),
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontFamily = AmiriArabicFontFamily,
                                            fontSize = (24 * fontSizeMultiplier).sp,
                                            lineHeight = (46 * fontSizeMultiplier).sp
                                        ),
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                        }

                        // Bengali Transliteration Section (বাংলা উচ্চারণ)
                        if (showTransliteration && !surah.transliterationBangla.isNullOrBlank()) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "বিশুদ্ধ বাংলা উচ্চারণ",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    IconButton(
                                        onClick = {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = ClipData.newPlainText("বাংলা উচ্চারণ", surah.transliterationBangla)
                                            clipboard.setPrimaryClip(clip)
                                            Toast.makeText(context, "উচ্চারণ কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "কপি",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f)
                                ) {
                                    Text(
                                        text = surah.transliterationBangla,
                                        modifier = Modifier.padding(14.dp),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = (16 * fontSizeMultiplier).sp,
                                            lineHeight = (28 * fontSizeMultiplier).sp
                                        ),
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        // Bengali Meaning Section (বাংলা অর্থ)
                        if (showTranslation && !surah.translationBangla.isNullOrBlank()) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "সহজ ও প্রাঞ্জল বাংলা অর্থ",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    IconButton(
                                        onClick = {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = ClipData.newPlainText("বাংলা অর্থ", surah.translationBangla)
                                            clipboard.setPrimaryClip(clip)
                                            Toast.makeText(context, "অর্থ কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "কপি",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                ) {
                                    Text(
                                        text = surah.translationBangla,
                                        modifier = Modifier.padding(14.dp),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = (15 * fontSizeMultiplier).sp,
                                            lineHeight = (26 * fontSizeMultiplier).sp
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
