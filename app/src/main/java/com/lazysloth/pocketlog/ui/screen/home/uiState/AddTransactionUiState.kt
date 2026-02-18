package com.lazysloth.pocketlog.ui.screen.home.uiState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lazysloth.pocketlog.database.data.TransactionType

data class AddTransactionUiState(
    val addAmount: String = "0.0",
    val expanded: Boolean = false,
    val selectedType: TransactionType = TransactionType.DEBIT,
    val inputNote: String = "",
    val inputDescription: String = ""
)
//var addAmount by remember { mutableStateOf("0.0") }
//var expanded by remember { mutableStateOf(false) }

//var selectedType by remember { mutableStateOf(TransactionType.DEBIT) }
//var inputNote by remember {mutableStateOf("")}
//var inputDescription by remember { mutableStateOf("") }