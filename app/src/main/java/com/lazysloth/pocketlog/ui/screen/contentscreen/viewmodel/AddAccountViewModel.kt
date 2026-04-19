package com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.data.Account
import com.lazysloth.pocketlog.database.data.Account1
import com.lazysloth.pocketlog.database.repository.AccountRepository
import com.lazysloth.pocketlog.di.UserPersists
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddAccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddAccountViewModel(
    private val accountRepository: AccountRepository,
    private val userPersists: UserPersists
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddAccountUiState())
    val uiState: StateFlow<AddAccountUiState> = _uiState.asStateFlow()

    fun onInitialBalanceChange(newBalance: String) {
        _uiState.update {
            it.copy(
                initialBalance = newBalance
            )
        }
    }

    fun onAccountNameChange(newName: String) {
        _uiState.update {
            it.copy(
                accountName = newName
            )
        }
    }

    fun onExpandAccountType(expanded: Boolean) {
        _uiState.update {
            it.copy(
                accountTypeExpanded = expanded
            )
        }
    }

    fun onAccountSelected(accountType: Account) {
        _uiState.update {
            it.copy(
                accountType = accountType
            )
        }
    }

    fun saveAccount() {
        viewModelScope.launch{
            val userId = userPersists.currentId
            accountRepository.saveAccount(_uiState.value.toAccount(userId))
        }
    }

    fun AddAccountUiState.toAccount(userId: Int): Account1 = Account1(
        name = accountName,
        balance = initialBalance.toDoubleOrNull() ?: 0.0,
        type = accountType,
        userId = userId

    )
}
