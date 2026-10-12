package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.learning.LearningCategory
import com.example.data.learning.LearningItem
import com.example.data.learning.NamazLearningData
import com.example.ui.components.AppFooter
import com.example.ui.theme.AmiriArabicFontFamily

@Composable
fun LearningScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf("all") }
    var expandedItemIds by remember { mutableStateOf(setOf<String>()) }

    val filteredItems = remember(searchQuery, selectedCategoryId) {
        val categories = if (selectedCategoryId == "all") {
            NamazLearningData.CATEGORIES
        } else {
            NamazLearningData.CATEGORIES.filter { it.id == selectedCategoryId }
        }

        val allItems = categories.flatMap { cat ->
            cat.items.map { item -> Pair(cat.titleBangla, item) }
        }

        if (searchQuery.isBlank()) {
            allItems
        } else {
            val q = searchQuery.trim().lowercase()
            allItems.filter { (_, item) ->
                item.titleBangla.contains(q) ||
                item.subtitleBangla.contains(q) ||
                (item.meaningBangla?.contains(q) == true) ||
                (item.transliterationBangla?.contains(q) == true) ||
                (item.arabicText?.contains(q) == true) ||
                item.steps.any { it.contains(q) }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("learning_screen_content"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "সহজ নামাজ শিক্ষা",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "বিশুদ্ধ আরবি দু'আ, উচ্চারণ, বাংলা অর্থ ও বিধিবিধান",
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
                    .testTag("learning_search_input"),
                placeholder = { Text("দু'আ, সূরা, ওজু বা নিয়ম খুঁজুন...") },
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

        // Category Filter Chips
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedCategoryId == "all",
                        onClick = { selectedCategoryId = "all" },
                        label = { Text("সকল অধ্যায়") },
                        modifier = Modifier.testTag("learning_chip_all")
                    )
                }
                items(NamazLearningData.CATEGORIES) { cat ->
                    FilterChip(
                        selected = selectedCategoryId == cat.id,
                        onClick = { selectedCategoryId = cat.id },
                        label = { Text(cat.titleBangla) },
                        modifier = Modifier.testTag("learning_chip_${cat.id}")
                    )
                }
            }
        }

        // Learning Items List
        if (filteredItems.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "কোনো বিষয় পাওয়া যায়নি",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(filteredItems, key = { it.second.id }) { (categoryName, item) ->
                val isExpanded = expandedItemIds.contains(item.id)
                LearningExpandableCard(
                    categoryName = categoryName,
                    item = item,
                    isExpanded = isExpanded,
                    onToggle = {
                        expandedItemIds = if (isExpanded) {
                            expandedItemIds - item.id
                        } else {
                            expandedItemIds + item.id
                        }
                    }
                )
            }
        }

        item {
            AppFooter(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp))
        }
    }
}

@Composable
private fun LearningExpandableCard(
    categoryName: String,
    item: LearningItem,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "expandRotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("learning_card_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 3.dp else 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.titleBangla,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$categoryName • ${item.subtitleBangla}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(
                    onClick = onToggle,
                    modifier = Modifier.rotate(rotation)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "সংক্ষেপ করুন" else "বিস্তারিত দেখুন",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HorizontalDivider()

                    // Arabic Text
                    if (!item.arabicText.isNullOrBlank()) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                        ) {
                            Text(
                                text = item.arabicText,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontFamily = AmiriArabicFontFamily,
                                    fontSize = 22.sp,
                                    lineHeight = 42.sp
                                ),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    // Transliteration
                    if (!item.transliterationBangla.isNullOrBlank()) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "উচ্চারণ (বাংলায়):",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.transliterationBangla,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Meaning
                    if (!item.meaningBangla.isNullOrBlank()) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "অনুবাদ ও অর্থ:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.meaningBangla,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }

                    // Numbered Steps
                    if (item.steps.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            item.steps.forEach { step ->
                                Text(
                                    text = step,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }

                    // Additional Explanation
                    if (!item.explanationBangla.isNullOrBlank()) {
                        Text(
                            text = item.explanationBangla,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}
