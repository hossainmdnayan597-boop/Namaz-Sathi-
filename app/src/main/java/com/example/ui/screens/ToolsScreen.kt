package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.database.PrayerLog
import com.example.data.database.TasbihItem
import com.example.data.preferences.UserSettings
import com.example.ui.MainViewModel

@Composable
fun ToolsScreen(
    viewModel: MainViewModel,
    userSettings: UserSettings,
    selectedSubTab: Int,
    onSubTabChanged: (Int) -> Unit,
    compassHeading: Float,
    qiblaBearing: Double,
    distanceKm: Double,
    selectedDateStr: String,
    todayLog: PrayerLog?,
    recentLogs: List<PrayerLog>,
    tasbihItems: List<TasbihItem>,
    selectedTasbihIndex: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("tools_screen_container")
    ) {
        // Tab Row at the top
        TabRow(
            selectedTabIndex = selectedSubTab,
            modifier = Modifier.fillMaxWidth().testTag("tools_tab_row")
        ) {
            Tab(
                selected = selectedSubTab == 0,
                onClick = { onSubTabChanged(0) },
                text = { Text("কিবলা") },
                icon = { Icon(Icons.Default.Explore, contentDescription = "কিবলা") },
                modifier = Modifier.testTag("tools_tab_qibla")
            )
            Tab(
                selected = selectedSubTab == 1,
                onClick = { onSubTabChanged(1) },
                text = { Text("তাসবীহ") },
                icon = { Icon(Icons.Default.Fingerprint, contentDescription = "তাসবীহ") },
                modifier = Modifier.testTag("tools_tab_tasbih")
            )
            Tab(
                selected = selectedSubTab == 2,
                onClick = { onSubTabChanged(2) },
                text = { Text("ট্র্যাকার") },
                icon = { Icon(Icons.Default.CheckCircle, contentDescription = "ট্র্যাকার") },
                modifier = Modifier.testTag("tools_tab_tracker")
            )
            Tab(
                selected = selectedSubTab == 3,
                onClick = { onSubTabChanged(3) },
                text = { Text("কুইজ") },
                icon = { Icon(Icons.Default.Quiz, contentDescription = "কুইজ") },
                modifier = Modifier.testTag("tools_tab_quiz")
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (selectedSubTab) {
                0 -> QiblaScreen(
                    viewModel = viewModel,
                    userSettings = userSettings,
                    compassHeading = compassHeading,
                    qiblaBearing = qiblaBearing,
                    distanceKm = distanceKm
                )
                1 -> TasbihScreen(
                    viewModel = viewModel,
                    tasbihItems = tasbihItems,
                    selectedIndex = selectedTasbihIndex
                )
                2 -> TrackerScreen(
                    viewModel = viewModel,
                    selectedDateStr = selectedDateStr,
                    todayLog = todayLog,
                    recentLogs = recentLogs
                )
                3 -> IslamicQuizScreen(
                    onBack = { onSubTabChanged(0) }
                )
            }
        }
    }
}
