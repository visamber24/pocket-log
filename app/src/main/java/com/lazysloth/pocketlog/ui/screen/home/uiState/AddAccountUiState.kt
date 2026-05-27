package com.lazysloth.pocketlog.ui.screen.home.uiState

import com.lazysloth.pocketlog.data.AccountType
import com.lazysloth.pocketlog.data.Account

data class AddAccountUiState(
    val id: Long = 0,
    val accountName: String = "",
    val initialBalance: String = "0.0",
    val currentBalance: String = "0.0",
    val accountTypeExpanded: Boolean = false,
    val accountType: AccountType = AccountType.Cash,
    val accountTypes: List<AccountType> = AccountType.entries
) {
}

fun AddAccountUiState.toAccount(userId: String?): Account = Account(
    id = id,
    userId = userId,
    name = accountName,
    balance = initialBalance.toDouble(),
    currentBalance = currentBalance.toDoubleOrNull() ?: 0.0,
    type = accountType
)

fun Account.toAddAccountUiState(): AddAccountUiState = AddAccountUiState(
    id = id,
    accountName = name,
    initialBalance = balance.toString(),
    currentBalance = currentBalance.toString(),
    accountType = type
)
