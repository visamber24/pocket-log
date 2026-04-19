package com.lazysloth.pocketlog.ui.screen.home.uiState

import com.lazysloth.pocketlog.database.data.Account

data class AddAccountUiState(
    val accountName: String = "",
    val initialBalance: String = "0.0",
    val accountType: Account = Account.Cash,
    val accounts: List<Account> = Account.entries
) {
}