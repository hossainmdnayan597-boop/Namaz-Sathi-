package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.preferences.UserSettings
import com.example.ui.MainViewModel
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun QiblaScreen(
    viewModel: MainViewModel,
    userSettings: UserSettings,
    compassHeading: Float,
    qiblaBearing: Double,
    distanceKm: Double
) {
    // Difference between heading and Qibla
    var diffAngle = (qiblaBearing.toFloat() - compassHeading + 360f) % 360f
    if (diffAngle > 180f) diffAngle -= 360f
    val isAligned = abs(diffAngle) <= 4.0f

    // Trigger haptic when just aligned
    var wasAligned by remember { mutableStateOf(false) }
    LaunchedEffect(isAligned) {
        if (isAligned && !wasAligned) {
            viewModel.vibrate(60)
        }
        wasAligned = isAligned
    }

    val statusColor by animateColorAsState(
        targetValue = if (isAligned) Color(0xFF10B981) else MaterialTheme.colorScheme.primary,
        label = "alignedColor"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("qibla_screen_content"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title & Location Header
        Text(
            text = "কিবলা কম্পাস",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${userSettings.cityName} (${String.format("%.2f°N, %.2f°E", userSettings.latitude, userSettings.longitude)})",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Live Alignment Banner
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (isAligned) Color(0xFFD1FAE5) else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isAligned) Icons.Default.CheckCircle else Icons.Default.Explore,
                    contentDescription = null,
                    tint = if (isAligned) Color(0xFF047857) else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isAligned) "মাশাআল্লাহ! কিবলামুখী হয়েছেন" else "কিবলার দিকে মোবাইলটি ঘোরান",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isAligned) Color(0xFF064E3B) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (isAligned)
                            "আপনার মুখ সরাসরি পবিত্র কাবা শরীফের দিকে রয়েছে"
                        else
                            "কিবলা ${if (diffAngle > 0) "ডানদিকে" else "বামদিকে"} ${abs(diffAngle).toInt()}° দূরত্বে",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isAligned) Color(0xFF064E3B) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Animated Compass Dial
        Box(
            modifier = Modifier
                .size(290.dp)
                .testTag("qibla_compass_dial"),
            contentAlignment = Alignment.Center
        ) {
            // Compass Canvas
            CompassDialCanvas(
                heading = compassHeading,
                qiblaBearing = qiblaBearing.toFloat(),
                isAligned = isAligned,
                primaryColor = MaterialTheme.colorScheme.primary,
                alignedColor = Color(0xFF10B981)
            )

            // Center Info Badge
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp,
                modifier = Modifier.size(76.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${compassHeading.toInt()}°",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                    Text(
                        text = "দিক",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Metrics Grid (Qibla Azimuth & Kaaba Distance)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "কিবলার কোণ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = String.format("%.1f°", qiblaBearing),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "উত্তর দিক হতে",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "কাবার দূরত্ব",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = String.format("%,d কিমি", distanceKm.toInt()),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "মক্কা মুকাররমা",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calibration / Usage Instructions
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "সঠিক কিবলা নির্ণয়ের পরামর্শ",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "১. ফোনটি কোনো সমতল স্থানে বা হাতের তালুতে আনুভূমিকভাবে রাখুন।\n২. কম্পাসের সঠিকতার জন্য কোনো চৌম্বকীয় বস্তু বা ধাতু থেকে দূরে থাকুন।\n৩. কম্পাসে অসংলগ্নতা দেখা দিলে ফোনটিকে বাতাসে '8' (আট) আকারে দুই-তিনবার ঘোরান।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun CompassDialCanvas(
    heading: Float,
    qiblaBearing: Float,
    isAligned: Boolean,
    primaryColor: Color,
    alignedColor: Color
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = (size.minDimension / 2f) - 16.dp.toPx()

        // 1. Draw outer circle
        drawCircle(
            color = if (isAligned) alignedColor.copy(alpha = 0.25f) else Color(0x22888888),
            radius = radius + 8.dp.toPx()
        )
        drawCircle(
            color = if (isAligned) alignedColor else Color(0xFF64748B),
            radius = radius,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
        )

        // 2. Rotate the compass rose with -heading
        rotate(-heading, pivot = center) {
            // Draw 360 degree graduation ticks
            for (angle in 0 until 360 step 15) {
                val rad = Math.toRadians(angle.toDouble())
                val isMajor = angle % 90 == 0
                val tickLen = if (isMajor) 14.dp.toPx() else 7.dp.toPx()
                val start = Offset(
                    (center.x + (radius - tickLen) * sin(rad)).toFloat(),
                    (center.y - (radius - tickLen) * cos(rad)).toFloat()
                )
                val end = Offset(
                    (center.x + radius * sin(rad)).toFloat(),
                    (center.y - radius * cos(rad)).toFloat()
                )
                drawLine(
                    color = if (isMajor) primaryColor else Color(0x66888888),
                    start = start,
                    end = end,
                    strokeWidth = if (isMajor) 2.5.dp.toPx() else 1.2.dp.toPx()
                )
            }

            // Draw North pointer (Red)
            val northTip = Offset(center.x, center.y - radius + 14.dp.toPx())
            val northLeft = Offset(center.x - 7.dp.toPx(), center.y - radius + 32.dp.toPx())
            val northRight = Offset(center.x + 7.dp.toPx(), center.y - radius + 32.dp.toPx())
            val northPath = Path().apply {
                moveTo(northTip.x, northTip.y)
                lineTo(northLeft.x, northLeft.y)
                lineTo(northRight.x, northRight.y)
                close()
            }
            drawPath(northPath, Color(0xFFEF4444))

            // 3. Draw Qibla pointer needle (Golden/Green Kaaba indicator)
            rotate(qiblaBearing, pivot = center) {
                val qiblaTip = Offset(center.x, center.y - radius + 2.dp.toPx())
                val qiblaLeft = Offset(center.x - 12.dp.toPx(), center.y - radius + 28.dp.toPx())
                val qiblaRight = Offset(center.x + 12.dp.toPx(), center.y - radius + 28.dp.toPx())
                val qiblaBase = Offset(center.x, center.y - 42.dp.toPx())

                val needlePath = Path().apply {
                    moveTo(qiblaTip.x, qiblaTip.y)
                    lineTo(qiblaRight.x, qiblaRight.y)
                    lineTo(qiblaBase.x, qiblaBase.y)
                    lineTo(qiblaLeft.x, qiblaLeft.y)
                    close()
                }

                // Needle fill
                drawPath(
                    needlePath,
                    if (isAligned) alignedColor else Color(0xFFD97706)
                )

                // Kaaba marker circle
                drawCircle(
                    color = if (isAligned) alignedColor else Color(0xFFB45309),
                    radius = 8.dp.toPx(),
                    center = Offset(center.x, center.y - radius + 12.dp.toPx())
                )
            }
        }
    }
}
