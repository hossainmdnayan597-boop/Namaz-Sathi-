package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.data.quiz.IslamicQuizData
import com.example.data.quiz.QuizCategory
import com.example.data.quiz.QuizQuestion
import com.example.ui.components.AppFooter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IslamicQuizScreen(
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) } // 0=Play Quiz, 1=Study Question Bank, 2=Daily Challenge

    // Active Quiz State
    var selectedCategory by remember { mutableStateOf(QuizCategory.ALL) }
    var questionCount by remember { mutableStateOf(10) }
    var isQuizActive by remember { mutableStateOf(false) }
    var quizQuestions by remember { mutableStateOf<List<QuizQuestion>>(emptyList()) }
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var isQuizFinished by remember { mutableStateOf(false) }

    // Track previously answered question IDs in session to prevent repeats
    val answeredQuestionIds = remember { mutableSetOf<Int>() }

    fun vibrateDevice(isCorrect: Boolean) {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vm?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
            if (vibrator?.hasVibrator() == true) {
                if (isCorrect) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 50, 60, 50), -1))
                }
            }
        } catch (_: Exception) {}
    }

    fun startNewQuiz(category: QuizCategory, count: Int) {
        selectedCategory = category
        questionCount = count
        // Anti-repetition: exclude recently answered question IDs
        val drawnQuestions = IslamicQuizData.getRandomQuiz(category, count, answeredQuestionIds)
        answeredQuestionIds.addAll(drawnQuestions.map { it.id })
        if (answeredQuestionIds.size > 160) {
            val keep = answeredQuestionIds.toList().takeLast(60).toSet()
            answeredQuestionIds.clear()
            answeredQuestionIds.addAll(keep)
        }
        quizQuestions = drawnQuestions
        currentQuestionIndex = 0
        selectedOptionIndex = null
        isAnswerSubmitted = false
        score = 0
        isQuizFinished = false
        isQuizActive = true
    }

    fun startDailyChallenge() {
        selectedCategory = QuizCategory.ALL
        val dailyQuestions = IslamicQuizData.getDailyQuiz()
        quizQuestions = dailyQuestions
        answeredQuestionIds.addAll(dailyQuestions.map { it.id })
        questionCount = dailyQuestions.size
        currentQuestionIndex = 0
        selectedOptionIndex = null
        isAnswerSubmitted = false
        score = 0
        isQuizFinished = false
        isQuizActive = true
        selectedTab = 0
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "ইসলামিক কুইজ ও জ্ঞানকোষ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "দ্বীনি জ্ঞান যাচাই ও শিক্ষণীয় প্রশ্নোত্তর",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (onBack != null || isQuizActive) {
                        IconButton(
                            onClick = {
                                if (isQuizActive) {
                                    isQuizActive = false
                                } else {
                                    onBack?.invoke()
                                }
                            },
                            modifier = Modifier.testTag("quiz_back_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরে যান")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier.fillMaxSize().testTag("islamic_quiz_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Mode Tabs (if not currently answering a quiz)
            if (!isQuizActive) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.fillMaxWidth().testTag("quiz_tab_row")
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("কুইজ খেলুন") },
                        icon = { Icon(Icons.Default.Quiz, contentDescription = null) },
                        modifier = Modifier.testTag("quiz_tab_play")
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("প্রশ্নভাণ্ডার") },
                        icon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
                        modifier = Modifier.testTag("quiz_tab_study")
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("দৈনিক চ্যালেঞ্জ") },
                        icon = { Icon(Icons.Default.EmojiEvents, contentDescription = null) },
                        modifier = Modifier.testTag("quiz_tab_daily")
                    )
                }
            }

            // Body Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                if (isQuizActive) {
                    if (isQuizFinished) {
                        QuizResultView(
                            score = score,
                            totalQuestions = quizQuestions.size,
                            category = selectedCategory,
                            onPlayAgain = { startNewQuiz(selectedCategory, questionCount) },
                            onNewCategory = { isQuizActive = false },
                            onStudy = {
                                isQuizActive = false
                                selectedTab = 1
                            }
                        )
                    } else if (quizQuestions.isNotEmpty() && currentQuestionIndex < quizQuestions.size) {
                        val currentQ = quizQuestions[currentQuestionIndex]
                        QuizQuestionView(
                            question = currentQ,
                            currentIndex = currentQuestionIndex,
                            totalCount = quizQuestions.size,
                            selectedOption = selectedOptionIndex,
                            isSubmitted = isAnswerSubmitted,
                            score = score,
                            onSelectOption = { optionIdx ->
                                if (!isAnswerSubmitted) {
                                    selectedOptionIndex = optionIdx
                                    isAnswerSubmitted = true
                                    val isCorrect = (optionIdx == currentQ.correctOptionIndex)
                                    if (isCorrect) score++
                                    vibrateDevice(isCorrect)
                                }
                            },
                            onNext = {
                                if (currentQuestionIndex + 1 < quizQuestions.size) {
                                    currentQuestionIndex++
                                    selectedOptionIndex = null
                                    isAnswerSubmitted = false
                                } else {
                                    isQuizFinished = true
                                }
                            },
                            onQuit = { isQuizActive = false }
                        )
                    }
                } else {
                    when (selectedTab) {
                        0 -> QuizSetupView(
                            selectedCategory = selectedCategory,
                            questionCount = questionCount,
                            onCategorySelect = { selectedCategory = it },
                            onCountSelect = { questionCount = it },
                            onStart = { startNewQuiz(selectedCategory, questionCount) },
                            onQuickDaily = { startDailyChallenge() }
                        )
                        1 -> QuestionBankExplorerView()
                        2 -> DailyChallengeSetupView(
                            onStartDaily = { startDailyChallenge() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuizSetupView(
    selectedCategory: QuizCategory,
    questionCount: Int,
    onCategorySelect: (QuizCategory) -> Unit,
    onCountSelect: (Int) -> Unit,
    onStart: () -> Unit,
    onQuickDaily: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))
            // Hero Welcome Card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth().testTag("quiz_hero_banner")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🕌", fontSize = 26.sp)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "ইসলামিক জ্ঞান চর্চা করুন",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "পবিত্র কুরআন, নবী-রাসূল ও দ্বীনি বিষয়ে কুইজ খেলে নিজের ঈমানি জ্ঞান শাণিত করুন।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Daily Challenge Fast Action
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFEF3C7) // Light Amber
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onQuickDaily() }
                    .testTag("quiz_daily_banner_click")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Text(text = "⭐", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "আজকের দৈনিক ৫০টি কুইজ চ্যালেঞ্জ",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                            Text(
                                text = "প্রতিদিনের ৫০টি বিশেষ অনন্য ইসলামিক প্রশ্ন",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB45309)
                            )
                        }
                    }

                    FilledTonalButton(
                        onClick = onQuickDaily,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = Color(0xFFD97706),
                            contentColor = Color.White
                        )
                    ) {
                        Text("৫০ প্রশ্ন খেলুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 1. Select Category
        item {
            Text(
                text = "১. বিষয় / ক্যাটাগরি বেছে নিন",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        val categories = QuizCategory.values()
        items(categories.size) { idx ->
            val cat = categories[idx]
            val isSelected = cat == selectedCategory
            val qCount = if (cat == QuizCategory.ALL) IslamicQuizData.questions.size else IslamicQuizData.getQuestionsByCategory(cat).size

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategorySelect(cat) }
                    .testTag("quiz_cat_${cat.name}")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = cat.iconEmoji, fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = cat.titleBangla,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                            Text(
                                text = cat.subtitleBangla,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    ) {
                        Text(
                            text = "$qCount টি প্রশ্ন",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // 2. Select Question Count
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "২. প্রশ্নের সংখ্যা নির্ধারণ করুন",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(10, 20, 30, 50).forEach { count ->
                    val isSelected = questionCount == count
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onCountSelect(count) }
                            .testTag("quiz_count_$count")
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$count টি",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Start Quiz Button
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onStart,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("quiz_start_button")
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "কুইজ শুরু করুন",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // App Footer with Md. Nayan Hossain and Facebook link
        item {
            AppFooter(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
private fun QuizQuestionView(
    question: QuizQuestion,
    currentIndex: Int,
    totalCount: Int,
    selectedOption: Int?,
    isSubmitted: Boolean,
    score: Int,
    onSelectOption: (Int) -> Unit,
    onNext: () -> Unit,
    onQuit: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Progress Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "প্রশ্ন ${currentIndex + 1} / $totalCount",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "${question.category.iconEmoji} ${question.category.titleBangla}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFD1FAE5) // light green
                ) {
                    Text(
                        text = "স্কোর: $score",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF047857),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / totalCount },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        // Question Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth().testTag("quiz_current_question_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = question.question,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 28.sp
                    )
                }
            }
        }

        // 4 Options
        itemsIndexed(question.options) { optionIdx, optionText ->
            val isOptionSelected = selectedOption == optionIdx
            val isCorrectOption = optionIdx == question.correctOptionIndex

            val (bgColor, borderColor, textColor) = when {
                !isSubmitted -> {
                    if (isOptionSelected) {
                        Triple(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    } else {
                        Triple(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.outlineVariant,
                            MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                isCorrectOption -> {
                    // Correct option lights up green
                    Triple(
                        Color(0xFFD1FAE5),
                        Color(0xFF059669),
                        Color(0xFF065F46)
                    )
                }
                isOptionSelected -> {
                    // User selected this and it was wrong -> red
                    Triple(
                        Color(0xFFFEE2E2),
                        Color(0xFFDC2626),
                        Color(0xFF991B1B)
                    )
                }
                else -> {
                    Triple(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = bgColor,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !isSubmitted) { onSelectOption(optionIdx) }
                    .testTag("quiz_option_$optionIdx")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(borderColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSubmitted && isCorrectOption) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(20.dp))
                        } else if (isSubmitted && isOptionSelected && !isCorrectOption) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(20.dp))
                        } else {
                            val prefix = when (optionIdx) {
                                0 -> "ক"
                                1 -> "খ"
                                2 -> "গ"
                                else -> "ঘ"
                            }
                            Text(
                                text = prefix,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = optionText,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (isOptionSelected || (isSubmitted && isCorrectOption)) FontWeight.Bold else FontWeight.Normal,
                        color = textColor,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Detailed Explanation Card (Revealed after answering)
        if (isSubmitted) {
            item {
                AnimatedVisibility(visible = isSubmitted) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedOption == question.correctOptionIndex) Color(0xFFECFDF5) else Color(0xFFFFFBEB)
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("quiz_explanation_card")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (selectedOption == question.correctOptionIndex) Icons.Default.CheckCircle else Icons.Default.Info,
                                    contentDescription = null,
                                    tint = if (selectedOption == question.correctOptionIndex) Color(0xFF059669) else Color(0xFFD97706)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (selectedOption == question.correctOptionIndex) "মাশাআল্লাহ! সঠিক উত্তর" else "সঠিক উত্তরটি দেখে নিন",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedOption == question.correctOptionIndex) Color(0xFF065F46) else Color(0xFF92400E)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = question.explanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("quiz_next_button")
                ) {
                    Text(
                        text = if (currentIndex + 1 < totalCount) "পরবর্তী প্রশ্ন ➔" else "ফলাফল দেখুন 🏆",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Quit Button
        item {
            TextButton(
                onClick = onQuit,
                modifier = Modifier.fillMaxWidth().testTag("quiz_quit_button")
            ) {
                Text("কুইজ বন্ধ করুন", color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
private fun QuizResultView(
    score: Int,
    totalQuestions: Int,
    category: QuizCategory,
    onPlayAgain: () -> Unit,
    onNewCategory: () -> Unit,
    onStudy: () -> Unit
) {
    val percentage = ((score.toFloat() / totalQuestions) * 100).toInt()
    val (appreciationTitle, appreciationDesc, badgeEmoji) = when {
        percentage >= 90 -> Triple("মাশাআল্লাহ! অসাধারণ পারদর্শিতা!", "আপনার ইসলামিক জ্ঞানের গভীরতা প্রশংসনীয়। আল্লাহ আপনার জ্ঞান ও ঈমানকে আরও সমৃদ্ধ করুন।", "🏆")
        percentage >= 70 -> Triple("আলহামদুলিল্লাহ! চমৎকার ফলাফল!", "আপনি বেশ ভালো ইসলামিক জ্ঞান অর্জন করেছেন। নিয়মিত চর্চায় এটি আরও বিকশিত হবে।", "🌟")
        percentage >= 50 -> Triple("বেশ ভালো চেষ্টা করেছেন!", "আপনার দ্বীনি জ্ঞান বৃদ্ধির পথ চমৎকার। প্রশ্নভাণ্ডার পড়ে আরও উন্নতি করতে পারেন।", "📖")
        else -> Triple("আল্লাহ আপনার চেষ্টাকে কবুল করুন!", "নিয়মিত কুরআন, হাদিস ও ইসলামিক বিষয়াদি অধ্যয়নের মাধ্যমে জ্ঞান আরও বৃদ্ধি করুন।", "🌱")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            // Score Display Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth().testTag("quiz_result_card")
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = badgeEmoji, fontSize = 54.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = appreciationTitle,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = appreciationDesc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Big Score / Mark Pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 28.dp, vertical = 14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "অর্জিত মোট মার্ক",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$score / $totalQuestions",
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (percentage >= 70) Color(0xFFD1FAE5) else Color(0xFFFEF3C7)
                            ) {
                                Text(
                                    text = "মার্ক্স পার্সেন্টেজ: $percentage% • ${if (percentage >= 80) "গ্রেড: মাশাআল্লাহ A+" else if (percentage >= 60) "গ্রেড: আলহামদুলিল্লাহ A" else "গ্রেড: B"}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (percentage >= 70) Color(0xFF047857) else Color(0xFFB45309),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ScoreStatPill(title = "সঠিক উত্তর", value = "$score টি", color = Color(0xFF059669))
                        ScoreStatPill(title = "ভুল উত্তর", value = "${totalQuestions - score} টি", color = Color(0xFFDC2626))
                        ScoreStatPill(title = "মোট প্রশ্ন", value = "$totalQuestions টি", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        // Action Buttons
        item {
            Button(
                onClick = onPlayAgain,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("quiz_play_again_button")
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("আবার খেলুন", fontWeight = FontWeight.Bold)
            }
        }

        item {
            OutlinedButton(
                onClick = onNewCategory,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("quiz_change_category_button")
            ) {
                Text("অন্য বিষয় বেছে নিন", fontWeight = FontWeight.SemiBold)
            }
        }

        item {
            FilledTonalButton(
                onClick = onStudy,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("quiz_open_study_button")
            ) {
                Icon(Icons.Default.MenuBook, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("প্রশ্ন ও উত্তর ভাণ্ডার পড়ুন", fontWeight = FontWeight.SemiBold)
            }
        }

        item {
            AppFooter(modifier = Modifier.padding(vertical = 12.dp))
        }
    }
}

@Composable
private fun ScoreStatPill(title: String, value: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.12f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = color)
            Text(text = value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
private fun QuestionBankExplorerView() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf(QuizCategory.ALL) }

    val filteredQuestions = remember(searchQuery, selectedCategoryFilter) {
        val pool = if (selectedCategoryFilter == QuizCategory.ALL) {
            IslamicQuizData.questions
        } else {
            IslamicQuizData.questions.filter { it.category == selectedCategoryFilter }
        }
        if (searchQuery.isBlank()) {
            pool
        } else {
            pool.filter {
                it.question.contains(searchQuery, ignoreCase = true) ||
                it.explanation.contains(searchQuery, ignoreCase = true) ||
                it.options.any { opt -> opt.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("প্রশ্ন বা উত্তর অনুসন্ধান করুন...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "মুছুন")
                        }
                    }
                },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().testTag("question_bank_search")
            )
        }

        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(QuizCategory.values()) { cat ->
                    val isSelected = cat == selectedCategoryFilter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategoryFilter = cat },
                        label = { Text("${cat.iconEmoji} ${cat.titleBangla}") },
                        modifier = Modifier.testTag("filter_chip_${cat.name}")
                    )
                }
            }
        }

        item {
            Text(
                text = "মোট প্রশ্ন: ${filteredQuestions.size}টি",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        items(filteredQuestions, key = { it.id }) { question ->
            QuestionBankItemCard(question = question)
        }

        item {
            AppFooter(modifier = Modifier.padding(vertical = 12.dp))
        }
    }
}

@Composable
private fun QuestionBankItemCard(question: QuizQuestion) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .testTag("question_item_${question.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "${question.category.iconEmoji} ${question.category.titleBangla}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${question.id}. ${question.question}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Correct Answer Tag
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFD1FAE5)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "সঠিক উত্তর: ${question.options[question.correctOptionIndex]}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF065F46)
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = "বিকল্পসমূহ:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    question.options.forEachIndexed { idx, opt ->
                        val isCorrect = idx == question.correctOptionIndex
                        Text(
                            text = "• $opt ${if (isCorrect) "✓ (সঠিক)" else ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isCorrect) Color(0xFF059669) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (isCorrect) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "💡 ব্যাখ্যা ও দলিল:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = question.explanation,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyChallengeSetupView(
    onStartDaily: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFEF3C7)
                ),
                modifier = Modifier.fillMaxWidth().testTag("daily_challenge_card")
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🌟", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "আজকের দৈনিক ৫০টি ইসলামিক প্রশ্ন চ্যালেঞ্জ",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "প্রতিদিন সম্পূর্ণ নতুন ও অনন্য ৫০টি প্রশ্নের উত্তর দিয়ে দ্বীনি জ্ঞান যাচাই করুন। কোনো পুনরাবৃত্তি বা একই প্রশ্ন বারবার আসবে না!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB45309),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 5-Category Distribution Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFDE68A).copy(alpha = 0.7f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "আজকের ৫০টি প্রশ্নের ভারসাম্যপূর্ণ বণ্টন:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF78350F)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "📖 কুরআন ১০ • 🕊️ নবী-রাসূল ১০ • 🕌 ইবাদত ১০ • ⚔️ ইতিহাস ১০ • 💡 সাধারণ জ্ঞান ১০",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF92400E),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onStartDaily,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD97706)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("start_daily_challenge_button")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("৫০টি প্রশ্নের দৈনিক চ্যালেঞ্জ শুরু করুন", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }

        item {
            AppFooter(modifier = Modifier.padding(vertical = 12.dp))
        }
    }
}
