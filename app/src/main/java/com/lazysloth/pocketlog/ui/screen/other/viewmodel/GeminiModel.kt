package com.lazysloth.pocketlog.ui.screen.other.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.generationConfig
import com.lazysloth.pocketlog.data.Transaction
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddTransactionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class GeminiModel {
    var geminiFinished by mutableStateOf(false)

    //Gemini implementation
    val config = generationConfig {
        responseMimeType = "application/json"
        maxOutputTokens = 100
        temperature = 0f
    }
    val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-3.5-flash", generationConfig = config, )

    var response by mutableStateOf("")

    private val _extractedData = MutableStateFlow(AddTransactionUiState())
    val extractedData: StateFlow<AddTransactionUiState> = _extractedData.asStateFlow()
    suspend fun analyze(prompt: String): String {
        Log.d("Gemini", "Analyze started")
        val result =
            model.generateContent(
                "Return ONLY JSON." +
                        "{\n" +
                        " \"addAmount\":\"\",\n" +
                        " \"inputNote\":\"\",\n" +
                        " \"inputDescription\":\"\",\n }" +
                        "OCR: $prompt"
            )
        Log.d("Gemini", "Gemini returned")
        response = result.text ?: ""
        val jsonString = response.trimIndent()
        val lenientJson = Json {
            ignoreUnknownKeys = true // Safely skips extra JSON fields
            coerceInputValues = true // Falls back to defaults if types mismatch
        }
        _extractedData.value = lenientJson.decodeFromString(jsonString)
        Log.d("gemini", "json return -> ${_extractedData.value}")
        return result.text ?: ""
    }
}

