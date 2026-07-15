package com.lazysloth.pocketlog.ui.screen.other.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class GeminiModel {


    //Gemini implementation
    val config = generationConfig {
        responseMimeType = "application/json"
        maxOutputTokens = 300
        temperature = 0f
    }

        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-3.1-flash-lite", generationConfig = config)


    var response by mutableStateOf("")

    private val _extractedData = MutableStateFlow(GeminiExtraction())
    val extractedData: StateFlow<GeminiExtraction> = _extractedData.asStateFlow()
    suspend fun analyze(prompt: String){
        Log.d("Gemini", "Analyze started")
        try {
            val result =
                model.generateContent(
                    """
    Extract the following information from the OCR text.

    Return ONLY valid JSON.
    Do not include markdown.
    Do not include ```json.
    Do not include explanations.

    {
      "addAmount": "",
      "category1": "",
      "inputNote": "",
      "inputDescription": "",
      "icon": ""
    }

    Rules:
    - addAmount: numeric amount only (e.g. "2499")
    - category: choose the best category
    - icon: choose exactly one of:
      ic_food
      ic_travel
      ic_education
      ic_bills
      ic_entertainment
      ic_health
      ic_gift
      ic_transport
      ic_shopping

    OCR:
    $prompt
    """.trimIndent()
                )
            response = result.text ?: ""
            val jsonString = response.trimIndent()
            val lenientJson = Json {
                ignoreUnknownKeys = true // Safely skips extra JSON fields
                coerceInputValues = true // Falls back to defaults if types mismatch
            }
            try {
                _extractedData.value = lenientJson.decodeFromString(jsonString)
            } catch (e: Exception) {
                Log.e("Gemini", "Invalid JSON:\n$response", e)
            }
            Log.d("gemini", "json return -> ${_extractedData.value}")
        } catch (e: Exception) {
            Log.e("gemini", "Generation Failed", e)
        } finally {
            Log.d("Gemini", "Gemini returned")
        }
    }
}

data class GeminiExtraction(
    val addAmount: String = "",
    val category1: String = "",
    val inputNote: String = "",
    val inputDescription: String = "",
    val icon: String = ""
)