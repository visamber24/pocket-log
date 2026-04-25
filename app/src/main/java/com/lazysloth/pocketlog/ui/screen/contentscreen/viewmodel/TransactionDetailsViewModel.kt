package com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel

import androidx.lifecycle.ViewModel

data class TransactionDetailsUiState(
    val id: Int = 0,
    val amount: String = "",
    val accountList: List<String> = listOf(),
    val accountName: String = "",
    val category: String = "",
    val transactionType: String = "",
    val note: String = "",
    val description: String = "",
    val dateTime: String = "",
)

class TransactionDetailsViewModel : ViewModel() {

}