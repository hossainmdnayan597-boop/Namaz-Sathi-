package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MainViewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val userSettings by mainViewModel.userSettings.collectAsStateWithLifecycle()

            val isDarkTheme = when (userSettings.themeMode) {
                "LIGHT" -> false
                "DARK" -> true
                else -> isSystemInDarkTheme()
            }

            MyApplicationTheme(darkTheme = isDarkTheme) {
                NamazSathiApp(viewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun NamazSathiApp(viewModel: MainViewModel) {
    val userSettings by viewModel.userSettings.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val showSettings by viewModel.showSettings.collectAsStateWithLifecycle()
    val showCityPicker by viewModel.showCityPicker.collectAsStateWithLifecycle()
    val showCustomLocationDialog by viewModel.showCustomLocationDialog.collectAsStateWithLifecycle()
    val currentTimeMillis by viewModel.currentTimeMillis.collectAsStateWithLifecycle()

    val daySchedule by viewModel.daySchedule.collectAsStateWithLifecycle()
    val prayerStatus by viewModel.currentPrayerStatus.collectAsStateWithLifecycle()
    val todayLog by viewModel.todayPrayerLog.collectAsStateWithLifecycle()
    val recentLogs by viewModel.recentPrayerLogs.collectAsStateWithLifecycle()
    val selectedDateStr by viewModel.selectedDateString.collectAsStateWithLifecycle()
    val tasbihItems by viewModel.tasbihItems.collectAsStateWithLifecycle()
    val selectedTasbihIndex by viewModel.selectedTasbihIndex.collectAsStateWithLifecycle()
    val compassHeading by viewModel.compassHeading.collectAsStateWithLifecycle()
    val qiblaBearing by viewModel.qiblaBearing.collectAsStateWithLifecycle()
    val distanceKm by viewModel.distanceToKaabaKm.collectAsStateWithLifecycle()
    val toolsSubTab by viewModel.toolsSubTab.collectAsStateWithLifecycle()

    // Handle back button smoothly
    BackHandler(enabled = showSettings || selectedTab != 0) {
        if (showSettings) {
            viewModel.openSettings(false)
        } else if (selectedTab != 0) {
            viewModel.setSelectedTab(0)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (!showSettings) {
                NavigationBar(
                    modifier = Modifier.testTag("main_navigation_bar"),
                    tonalElevation = 8.dp
                ) {
                    val navItems = listOf(
                        Triple(0, "সময়", Icons.Default.AccessTime),
                        Triple(1, "কুরআন", Icons.Default.MenuBook),
                        Triple(2, "হাদিস", Icons.Default.AutoStories),
                        Triple(3, "শিক্ষা", Icons.Default.School),
                        Triple(4, "টুলস", Icons.Default.Explore)
                    )

                    navItems.forEach { (index, title, icon) ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { viewModel.setSelectedTab(index) },
                            icon = { Icon(imageVector = icon, contentDescription = title) },
                            label = { Text(title) },
                            modifier = Modifier.testTag("nav_tab_$index")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (showSettings) {
                SettingsScreen(
                    viewModel = viewModel,
                    userSettings = userSettings,
                    onBack = { viewModel.openSettings(false) },
                    onOpenCityPicker = { viewModel.openCityPicker(true) },
                    onOpenCustomLocation = { viewModel.openCustomLocationDialog(true) }
                )
            } else {
                when (selectedTab) {
                    0 -> HomeScreen(
                        viewModel = viewModel,
                        userSettings = userSettings,
                        daySchedule = daySchedule,
                        prayerStatus = prayerStatus,
                        todayLog = todayLog,
                        currentTimeMillis = currentTimeMillis,
                        onOpenCityPicker = { viewModel.openCityPicker(true) },
                        onOpenSettings = { viewModel.openSettings(true) },
                        onNavigateToTracker = { viewModel.navigateToTool(2) },
                        onNavigateToQuran = { viewModel.setSelectedTab(1) },
                        onNavigateToHadith = { viewModel.setSelectedTab(2) },
                        onNavigateToLearning = { viewModel.setSelectedTab(3) },
                        onNavigateToQibla = { viewModel.navigateToTool(0) },
                        onNavigateToTasbih = { viewModel.navigateToTool(1) },
                        onNavigateToQuiz = { viewModel.navigateToTool(3) }
                    )
                    1 -> QuranScreen()
                    2 -> HadithScreen()
                    3 -> LearningScreen()
                    4 -> ToolsScreen(
                        viewModel = viewModel,
                        userSettings = userSettings,
                        selectedSubTab = toolsSubTab,
                        onSubTabChanged = { viewModel.setToolsSubTab(it) },
                        compassHeading = compassHeading,
                        qiblaBearing = qiblaBearing,
                        distanceKm = distanceKm,
                        selectedDateStr = selectedDateStr,
                        todayLog = todayLog,
                        recentLogs = recentLogs,
                        tasbihItems = tasbihItems,
                        selectedTasbihIndex = selectedTasbihIndex
                    )
                }
            }
        }
    }

    // City Picker Bottom Sheet
    if (showCityPicker) {
        CityPickerDialog(
            currentCityId = userSettings.cityId,
            onCitySelected = { city ->
                viewModel.selectCity(city)
            },
            onOpenCustomLocation = {
                viewModel.openCityPicker(false)
                viewModel.openCustomLocationDialog(true)
            },
            onDismiss = {
                viewModel.openCityPicker(false)
            }
        )
    }

    // Custom Location Dialog
    if (showCustomLocationDialog) {
        CustomLocationDialog(
            onDismiss = { viewModel.openCustomLocationDialog(false) },
            onSave = { name, lat, lon ->
                viewModel.saveCustomLocation(name, lat, lon)
            }
        )
    }
}
