package com.lazysloth.pocketlog.ui.screen.home.uiState

import com.lazysloth.pocketlog.database.data.Account
import com.lazysloth.pocketlog.database.data.Account1

data class AddAccountUiState(
    val id: Long = 0,
    val accountName: String = "",
    val initialBalance: String = "0.0",
    val accountTypeExpanded: Boolean = false,
    val accountType: Account = Account.Cash,
    val accounts: List<Account> = Account.entries
) {
}

fun AddAccountUiState.toAccount(userId: Int): Account1 = Account1(
    id = id,
    userId = userId,
    name = accountName,
    balance = initialBalance.toDouble(),
    type = accountType
)

fun Account1.toAddAccountUiState(): AddAccountUiState = AddAccountUiState(
    id = id,
    accountName = name,
    initialBalance = balance.toString(),
    accountType = type
)
