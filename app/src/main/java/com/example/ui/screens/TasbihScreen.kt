package com.example.ui.screens

import android.view.SoundEffectConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.database.TasbihItem
import com.example.data.preferences.UserSettings
import com.example.data.tasbih.TasbihRecord
import com.example.ui.MainViewModel
import com.example.ui.components.AppFooter
import com.example.ui.theme.AmiriArabicFontFamily
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbihScreen(
    viewModel: MainViewModel,
    userSettings: UserSettings = UserSettings(),
    tasbihItems: List<TasbihItem>,
    selectedIndex: Int
) {
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current
    val snackbarHostState = remember { SnackbarHostState() }

    var showAddDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showSaveDialog by remember { mutableStateOf(false) }
    var showHistoryDialog by remember { mutableStateOf(false) }
    var showEditCountDialog by remember { mutableStateOf(false) }
    var showMilestoneDialog by remember { mutableStateOf(false) }
    var isFullscreenMode by remember { mutableStateOf(false) }

    val savedRecords by viewModel.savedTasbihRecords.collectAsState()
    val currentItem = tasbihItems.getOrNull(selectedIndex) ?: tasbihItems.firstOrNull()

    // Watch for milestone completion (target reached / cycle completed)
    var lastCelebratedCycles by remember { mutableIntStateOf(currentItem?.totalCompletedCycles ?: 0) }
    LaunchedEffect(currentItem?.totalCompletedCycles) {
        val cycles = currentItem?.totalCompletedCycles ?: 0
        if (cycles > lastCelebratedCycles) {
            lastCelebratedCycles = cycles
            showMilestoneDialog = true
            viewModel.vibratePattern(longArrayOf(0, 100, 80, 150))
        }
    }

    // Function to handle counting increment with rich haptic and sound feedback
    val onIncrementCount = {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        if (userSettings.tasbihSound) {
            view.playSoundEffect(SoundEffectConstants.CLICK)
        }
        viewModel.countTasbih()
    }

    // Fullscreen Immersive Tap Mode
    if (isFullscreenMode && currentItem != null) {
        FullscreenTasbihView(
            item = currentItem,
            userSettings = userSettings,
            onIncrement = onIncrementCount,
            onClose = { isFullscreenMode = false }
        )
        return
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .testTag("tasbih_screen_content"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with Title & Quick Tool Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "ডিজিটাল তাসবীহ",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "হ্যাপটিক ফিডব্যাক সহ দৈনিক জিকির গণনা",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Vibration Toggle
                    IconButton(
                        onClick = {
                            viewModel.toggleTasbihVibration(!userSettings.tasbihVibration)
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        },
                        modifier = Modifier.testTag("toggle_vibration_button")
                    ) {
                        Icon(
                            imageVector = if (userSettings.tasbihVibration) Icons.Default.Vibration else Icons.Default.Smartphone,
                            contentDescription = if (userSettings.tasbihVibration) "কম্পন চালু" else "কম্পন বন্ধ",
                            tint = if (userSettings.tasbihVibration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        )
                    }

                    // Sound Toggle
                    IconButton(
                        onClick = {
                            viewModel.toggleTasbihSound(!userSettings.tasbihSound)
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        },
                        modifier = Modifier.testTag("toggle_sound_button")
                    ) {
                        Icon(
                            imageVector = if (userSettings.tasbihSound) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                            contentDescription = if (userSettings.tasbihSound) "শব্দ চালু" else "শব্দ বন্ধ",
                            tint = if (userSettings.tasbihSound) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        )
                    }

                    // Saved History Button
                    IconButton(
                        onClick = { showHistoryDialog = true },
                        modifier = Modifier.testTag("view_saved_records_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (savedRecords.isNotEmpty()) {
                                    Badge { Text("${savedRecords.size}") }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "সংরক্ষিত রেকর্ড",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // Add Custom Dhikr Button
                    IconButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier.testTag("add_custom_dhikr_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "নতুন যিকির",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dhikr Selector Horizontal Chips
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(tasbihItems) { index, item ->
                    FilterChip(
                        selected = index == selectedIndex,
                        onClick = {
                            viewModel.selectTasbih(index)
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        },
                        label = {
                            Text(
                                text = item.titleBangla,
                                fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        leadingIcon = if (index == selectedIndex) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        } else null,
                        modifier = Modifier.testTag("tasbih_chip_$index")
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Dhikr Arabic Text, Translation & Target Preset Card
            currentItem?.let { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("tasbih_info_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Arabic Text
                        Text(
                            text = item.arabic,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = AmiriArabicFontFamily,
                                fontSize = 28.sp,
                                lineHeight = 44.sp
                            ),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Bangla Title
                        Text(
                            text = item.titleBangla,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Bangla Meaning
                        Text(
                            text = "\"${item.meaningBangla}\"",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Target Selector Chips: 33, 100, 500, 1000, 0 (Unlimited)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "টার্গেট:",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            val targets = listOf(33 to "৩৩", 100 to "১০০", 500 to "৫০০", 1000 to "১০০০", 0 to "মুক্ত ∞")
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(targets) { (tVal, label) ->
                                    val isSelected = item.targetCount == tVal
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                        modifier = Modifier.clickable {
                                            viewModel.setTasbihTarget(tVal)
                                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        }
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Completed Rounds / Cycles Badge
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "রাউন্ড সম্পন্ন: ${item.totalCompletedCycles} বার",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Realistic Digital Tally Display Screen (LED Box)
            currentItem?.let { item ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .shadow(6.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF0F261C), // Deep Emerald LCD Background
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF2E6349))
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "DIGITAL TALLY",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF74A98F),
                                letterSpacing = 2.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = if (item.targetCount > 0) "GOAL: ${item.targetCount}" else "FREE MODE",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF74A98F),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Large Glowing Digital Numbers
                        Text(
                            text = String.format("%04d", item.currentCount),
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontSize = 48.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 4.sp
                            ),
                            color = Color(0xFF86EFAC) // Glowing emerald mint digits
                        )

                        // Target percentage progress text
                        if (item.targetCount > 0) {
                            val percent = ((item.currentCount.toFloat() / item.targetCount.toFloat()) * 100).toInt().coerceIn(0, 100)
                            Text(
                                text = "অগ্রগতি: $percent% (${item.currentCount}/${item.targetCount})",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFA7F3D0)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Big Interactive Tap Counter Button with Circular Radial Beads Ring
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val scale by animateFloatAsState(
                targetValue = if (isPressed) 0.91f else 1f,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 600f),
                label = "tasbihScale"
            )

            val currentCount = currentItem?.currentCount ?: 0
            val targetCount = currentItem?.targetCount ?: 0
            val progress = if (targetCount > 0) (currentCount.toFloat() / targetCount.toFloat()).coerceIn(0f, 1f) else 0f

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(250.dp)
                    .scale(scale)
            ) {
                // Outer Radial Bead Ring Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 8.dp.toPx()
                    val diameter = size.minDimension - strokeWidth
                    val radius = diameter / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)

                    // Background track
                    drawCircle(
                        color = Color(0x3310B981),
                        radius = radius,
                        center = center,
                        style = Stroke(width = strokeWidth)
                    )

                    // Active progress arc
                    if (targetCount > 0 && progress > 0f) {
                        drawArc(
                            brush = Brush.sweepGradient(
                                colors = listOf(Color(0xFF34D399), Color(0xFF059669), Color(0xFF10B981))
                            ),
                            startAngle = -90f,
                            sweepAngle = 360f * progress,
                            useCenter = false,
                            topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f),
                            size = Size(diameter, diameter),
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }

                    // Decorative bead points around the circle (33 bead markers)
                    val beadCount = if (targetCount in 1..99) targetCount else 33
                    for (i in 0 until beadCount) {
                        val angle = Math.toRadians((i * (360.0 / beadCount) - 90.0))
                        val beadX = center.x + (radius * cos(angle)).toFloat()
                        val beadY = center.y + (radius * sin(angle)).toFloat()
                        val isFilled = if (targetCount > 0) (i < currentCount) else false
                        drawCircle(
                            color = if (isFilled) Color(0xFF10B981) else Color(0x5594A3B8),
                            radius = if (isFilled) 4.dp.toPx() else 2.5.dp.toPx(),
                            center = Offset(beadX, beadY)
                        )
                    }
                }

                // Inner Main Tap Button Surface
                Surface(
                    modifier = Modifier
                        .size(196.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onIncrementCount()
                        }
                        .testTag("tasbih_counter_button"),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    shadowElevation = if (isPressed) 2.dp else 10.dp
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                                        MaterialTheme.colorScheme.primary
                                    )
                                )
                            )
                    ) {
                        // Inner ring border
                        Surface(
                            shape = CircleShape,
                            color = Color.Transparent,
                            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0x55FFFFFF)),
                            modifier = Modifier.size(176.dp)
                        ) {}

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "$currentCount",
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                fontSize = 54.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "ট্যাপ করুন",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFFD1FAE5),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Sub-actions: Undo (-1), Manual Edit, Fullscreen Mode
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Decrement (-1) Button
                OutlinedButton(
                    onClick = {
                        viewModel.decrementTasbih()
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    modifier = Modifier.testTag("decrement_tasbih_button"),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "কমিয়ে দিন", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("১ কমান (-১)")
                }

                // Edit Count Manually Button
                FilledTonalIconButton(
                    onClick = { showEditCountDialog = true },
                    modifier = Modifier.testTag("edit_count_button")
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "গণনা সম্পাদনা")
                }

                // Fullscreen Mode Toggle
                FilledTonalIconButton(
                    onClick = { isFullscreenMode = true },
                    modifier = Modifier.testTag("fullscreen_tasbih_button")
                ) {
                    Icon(imageVector = Icons.Default.Fullscreen, contentDescription = "ফুলস্ক্রিন মোড")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Action Buttons: SAVE COUNT & RESET COUNT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // SAVE COUNT BUTTON
                Button(
                    onClick = {
                        showSaveDialog = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("save_tasbih_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(imageVector = Icons.Default.BookmarkAdded, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                }

                // RESET COUNT BUTTON
                OutlinedButton(
                    onClick = { showResetDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("reset_tasbih_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("রিসেট করুন", fontWeight = FontWeight.Bold)
                }
            }

            // Custom Dhikr Delete Option
            if (currentItem?.isCustom == true) {
                Spacer(modifier = Modifier.height(10.dp))
                TextButton(
                    onClick = {
                        viewModel.deleteCustomTasbih(currentItem.id)
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.testTag("delete_custom_tasbih_button")
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("এই কাস্টম যিকিরটি মুছে ফেলুন")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            AppFooter()
        }
    }

    // Save Count Dialog
    if (showSaveDialog && currentItem != null) {
        SaveTasbihDialog(
            item = currentItem,
            onDismiss = { showSaveDialog = false },
            onSave = { note ->
                viewModel.saveCurrentTasbihSession(note)
                showSaveDialog = false
            }
        )
    }

    // Reset Confirmation Dialog
    if (showResetDialog && currentItem != null) {
        ResetTasbihDialog(
            currentCount = currentItem.currentCount,
            totalCycles = currentItem.totalCompletedCycles,
            onDismiss = { showResetDialog = false },
            onResetCountOnly = {
                viewModel.resetCurrentTasbih()
                showResetDialog = false
            },
            onResetAll = {
                viewModel.resetCurrentTasbihAndCycles()
                showResetDialog = false
            }
        )
    }

    // Saved Records History Dialog
    if (showHistoryDialog) {
        SavedTasbihHistoryDialog(
            records = savedRecords,
            onDismiss = { showHistoryDialog = false },
            onDeleteRecord = { viewModel.deleteSavedTasbihRecord(it) },
            onClearAll = { viewModel.clearAllSavedTasbihRecords() }
        )
    }

    // Edit Count Dialog
    if (showEditCountDialog && currentItem != null) {
        EditCountDialog(
            currentCount = currentItem.currentCount,
            onDismiss = { showEditCountDialog = false },
            onSaveCount = { newCount ->
                viewModel.setTasbihCount(newCount)
                showEditCountDialog = false
            }
        )
    }

    // Milestone Reached Dialog
    if (showMilestoneDialog && currentItem != null) {
        AlertDialog(
            onDismissRequest = { showMilestoneDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Celebration,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = "মাশাআল্লাহ! লক্ষ্য অর্জিত!",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "আপনি \"${currentItem.titleBangla}\" ${currentItem.targetCount} বার সম্পন্ন করেছেন।",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "পুনরায় ট্যাপ করলে পরবর্তী রাউন্ড গণনা শুরু হবে।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showMilestoneDialog = false },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("আলহামদুলিল্লাহ")
                }
            }
        )
    }

    // Add Custom Dhikr Dialog
    if (showAddDialog) {
        AddCustomDhikrDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { title, arabic, meaning, target ->
                viewModel.addCustomTasbih(title, arabic, meaning, target)
                showAddDialog = false
            }
        )
    }
}

// Fullscreen Tap Mode for uninterrupted dhikr
@Composable
private fun FullscreenTasbihView(
    item: TasbihItem,
    userSettings: UserSettings,
    onIncrement: () -> Unit,
    onClose: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f),
        label = "fullscreenScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF071B12))
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onIncrement()
            }
            .testTag("fullscreen_tasbih_container")
    ) {
        // Exit Fullscreen Floating Button
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
                .background(Color(0x33FFFFFF), CircleShape)
                .testTag("exit_fullscreen_button")
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "প্রস্থান করুন",
                tint = Color.White
            )
        }

        // Central Fullscreen Display
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.arabic,
                style = MaterialTheme.typography.displayMedium.copy(
                    fontFamily = AmiriArabicFontFamily,
                    fontSize = 36.sp,
                    lineHeight = 54.sp
                ),
                color = Color(0xFF86EFAC),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = item.titleBangla,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Big Count Number
            Text(
                text = "${item.currentCount}",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 90.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                color = Color.White
            )

            if (item.targetCount > 0) {
                Text(
                    text = "লক্ষ্য: ${item.targetCount} | রাউন্ড: ${item.totalCompletedCycles}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF6EE7B7)
                )
            } else {
                Text(
                    text = "রাউন্ড: ${item.totalCompletedCycles}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF6EE7B7)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0x22FFFFFF)
            ) {
                Text(
                    text = "স্ক্রিনের যেকোনো স্থানে স্পর্শ করে জিকির করুন",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFD1FAE5),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

// Save Tasbih Dialog
@Composable
private fun SaveTasbihDialog(
    item: TasbihItem,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("গণনা সংরক্ষণ করুন", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = item.titleBangla,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "বর্তমান গণনা: ${item.currentCount} বার",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (item.totalCompletedCycles > 0) {
                            Text(
                                text = "সমাপ্ত রাউন্ড: ${item.totalCompletedCycles}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("নোট / উপলক্ষ (ঐচ্ছিক)") },
                    placeholder = { Text("যেমন: ফজরের পর, বিশেষ দু'আ") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("save_tasbih_note_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(note.trim()) },
                modifier = Modifier.testTag("confirm_save_tasbih_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("সংরক্ষণ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}

// Reset Tasbih Dialog
@Composable
private fun ResetTasbihDialog(
    currentCount: Int,
    totalCycles: Int,
    onDismiss: () -> Unit,
    onResetCountOnly: () -> Unit,
    onResetAll: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("গণনা রিসেট করবেন?", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "আপনার বর্তমান গণনা ($currentCount) শূন্য (০) থেকে শুরু হবে। আপনি কোনটি রিসেট করতে চান?",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = onResetAll,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("সব রিসেট")
                }

                Button(
                    onClick = onResetCountOnly,
                    modifier = Modifier.testTag("confirm_reset_count_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("গণনা ০ করুন")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}

// Edit Count Dialog
@Composable
private fun EditCountDialog(
    currentCount: Int,
    onDismiss: () -> Unit,
    onSaveCount: (Int) -> Unit
) {
    var countText by remember { mutableStateOf("$currentCount") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("গণনা সরাসরি পরিবর্তন করুন", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "ম্যানুয়াল বা পূর্বের গণনা মিলিয়ে নিতে সংখ্যা প্রবেশ করান:",
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedTextField(
                    value = countText,
                    onValueChange = { countText = it },
                    label = { Text("গণনার সংখ্যা") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("manual_count_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val parsed = countText.toIntOrNull() ?: currentCount
                    onSaveCount(maxOf(0, parsed))
                },
                modifier = Modifier.testTag("save_manual_count_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("আপডেট করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}

// Saved Tasbih History Dialog
@Composable
private fun SavedTasbihHistoryDialog(
    records: List<TasbihRecord>,
    onDismiss: () -> Unit,
    onDeleteRecord: (String) -> Unit,
    onClearAll: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "সংরক্ষিত তাসবীহ রেকর্ড",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "মোট সেশন: ${records.size} টি",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (records.isNotEmpty()) {
                        IconButton(onClick = onClearAll) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "সব মুছুন",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (records.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                modifier = Modifier.size(54.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "কোনো সংরক্ষিত রেকর্ড নেই",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "তাসবীহ গণনার পর 'সংরক্ষণ করুন' চাপুন",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(records, key = { it.id }) { record ->
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = record.dhikrTitle,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "${record.count} বার ${if (record.targetCount > 0) "(টার্গেট: ${record.targetCount})" else ""}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        if (record.note.isNotBlank()) {
                                            Text(
                                                text = "নোট: ${record.note}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Text(
                                            text = record.formattedDate,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }

                                    IconButton(onClick = { onDeleteRecord(record.id) }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "মুছুন",
                                            tint = MaterialTheme.colorScheme.outline,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("বন্ধ করুন")
                }
            }
        }
    }
}

// Add Custom Dhikr Dialog
@Composable
private fun AddCustomDhikrDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String, Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var arabic by remember { mutableStateOf("") }
    var meaning by remember { mutableStateOf("") }
    var targetText by remember { mutableStateOf("100") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("নতুন যিকির যোগ করুন", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("যিকিরের নাম (বাংলা) *") },
                    placeholder = { Text("যেমন: হাসবুনাল্লাহু ওয়ানিমাল ওয়াকিল") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("custom_dhikr_title_input")
                )
                OutlinedTextField(
                    value = arabic,
                    onValueChange = { arabic = it },
                    label = { Text("আরবি পাঠ (ঐচ্ছিক)") },
                    placeholder = { Text("حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = meaning,
                    onValueChange = { meaning = it },
                    label = { Text("বাংলা অর্থ (ঐচ্ছিক)") },
                    placeholder = { Text("আল্লাহই আমাদের জন্য যথেষ্ট...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = targetText,
                    onValueChange = { targetText = it },
                    label = { Text("টার্গেট সংখ্যা (৩৩, ১০০ বা ইচ্ছামতো)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("custom_dhikr_target_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val target = targetText.toIntOrNull() ?: 100
                        onAdd(title.trim(), arabic.trim(), meaning.trim(), target)
                    }
                },
                modifier = Modifier.testTag("submit_custom_dhikr_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("যোগ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}
