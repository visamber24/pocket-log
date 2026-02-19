package com.lazysloth.pocketlog.ui.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.Transaction
import com.lazysloth.pocketlog.database.data.TransactionType
import com.lazysloth.pocketlog.database.repository.TransactionRepository
import com.lazysloth.pocketlog.ui.screen.home.uiState.Account
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddTransactionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTransactionScreenViewmodel(private val transactionRepository: TransactionRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()


    fun onAmountChange(newAmount: String) {
        _uiState.update {
            it.copy(addAmount = newAmount)
        }
    }

    fun onExpandedChange(expanded: Boolean) {
        _uiState.update {
            it.copy(
                expanded = expanded
            )
        }
    }

    fun onTransactionTypeSelected(type: TransactionType) {
        _uiState.update {
            it.copy(
                selectedType = type
            )
        }
    }
    fun onAccountSelected(account: Account){
        _uiState.update {
            it.copy(account = account)
        }
    }

    fun onNoteValueChange(note: String) {
        _uiState.update {
            it.copy(inputNote = note)
        }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(inputDescription = description) }
    }

    fun saveTransaction() {
        viewModelScope.launch {
            transactionRepository.insertTransaction(_uiState.value.toItem())
        }
    }
    fun AddTransactionUiState.toItem(): Transaction = Transaction(
        id = id,
        amount = addAmount.toDoubleOrNull()?:0.0,
        account = account.name ,
        category = options[0],
        transactionType =  selectedType ,
        note = inputNote,
        description = inputDescription,
//        date_time = 0
    )
}
