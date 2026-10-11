package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun CustomLocationDialog(
    onDismiss: () -> Unit,
    onSave: (name: String, lat: Double, lon: Double) -> Unit
) {
    var nameText by remember { mutableStateOf("আমার অবস্থান") }
    var latText by remember { mutableStateOf("23.8103") }
    var lonText by remember { mutableStateOf("90.4125") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("কাস্টম অবস্থান নির্ধারণ") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "যেকোনো নির্দিষ্ট স্থানের সঠিক অক্ষাংশ (Latitude) ও দ্রাঘিমাংশ (Longitude) লিখুন:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = nameText,
                    onValueChange = { nameText = it },
                    label = { Text("স্থানের নাম") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("custom_loc_name")
                )

                OutlinedTextField(
                    value = latText,
                    onValueChange = { latText = it },
                    label = { Text("অক্ষাংশ (যেমন: 23.8103)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("custom_loc_lat")
                )

                OutlinedTextField(
                    value = lonText,
                    onValueChange = { lonText = it },
                    label = { Text("দ্রাঘিমাংশ (যেমন: 90.4125)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("custom_loc_lon")
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val lat = latText.toDoubleOrNull()
                    val lon = lonText.toDoubleOrNull()
                    if (nameText.isBlank()) {
                        errorMessage = "স্থানের নাম লিখুন"
                    } else if (lat == null || lat < -90.0 || lat > 90.0) {
                        errorMessage = "সঠিক অক্ষাংশ দিন (-৯০ থেকে ৯০)"
                    } else if (lon == null || lon < -180.0 || lon > 180.0) {
                        errorMessage = "সঠিক দ্রাঘিমাংশ দিন (-১৮০ থেকে ১৮০)"
                    } else {
                        onSave(nameText.trim(), lat, lon)
                    }
                },
                modifier = Modifier.testTag("save_custom_location_button")
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
