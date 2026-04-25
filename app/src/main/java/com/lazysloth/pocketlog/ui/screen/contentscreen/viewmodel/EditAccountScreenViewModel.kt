package com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.data.Account
import com.lazysloth.pocketlog.database.repository.AccountRepository
import com.lazysloth.pocketlog.di.UserPersists
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddAccountUiState
import com.lazysloth.pocketlog.ui.screen.home.uiState.toAccount
import com.lazysloth.pocketlog.ui.screen.home.uiState.toAddAccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditAccountScreenViewModel(
    private val accountRepository: AccountRepository, private val userPersists: UserPersists
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddAccountUiState())
    val uiState: StateFlow<AddAccountUiState> = _uiState.asStateFlow()

    fun getAccountId(id: Long?) {
        viewModelScope.launch {
            try {
                val account = accountRepository.getAccountByAccountId(id).filterNotNull().first()
                println("account -> ${account.toAddAccountUiState()}")
                _uiState.value = account.toAddAccountUiState()


            } catch (e: Exception) {
                print("An exception occur -> $e ")
            }
        }
    }

    fun onInitialBalanceChange(newBalance: String) {
        _uiState.update {
            it.copy(
                initialBalance = newBalance
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

    fun onAccountNameChange(accountName: String) {
        _uiState.update {
            it.copy(
                accountName = accountName
            )
        }
    }

    fun onExpandedAccountType(expanded: Boolean) {
        _uiState.update {
            it.copy(
                accountTypeExpanded = expanded
            )
        }
    }

    fun onUpdate() {
        viewModelScope.launch {
            val userId = userPersists.currentId
            accountRepository.updateAccount(_uiState.value.toAccount(userId))
        }
    }


}