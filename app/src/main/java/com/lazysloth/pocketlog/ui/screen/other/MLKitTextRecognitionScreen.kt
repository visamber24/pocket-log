package com.lazysloth.pocketlog.ui.screen.other

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun MLKitTextRecognitionScreen() {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        CameraPreview { scannedText ->
//            text = scannedText
//        }
        Spacer(modifier = Modifier.height(16.dp))
        // Display Recognized Text
        Text(
            text = if (text.isNotEmpty()) "Detected Text: $text" else "No text detected",
            modifier = Modifier.padding(16.dp)
        )
    }
}