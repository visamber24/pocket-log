package com.lazysloth.pocketlog.ui.screen.home.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.Transaction
import com.lazysloth.pocketlog.database.data.Category
import com.lazysloth.pocketlog.database.data.TransactionType
import com.lazysloth.pocketlog.database.repository.TransactionRepository
import com.lazysloth.pocketlog.ui.screen.home.uiState.Account
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddTransactionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

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

    fun onOptionSelected(option: Category) {
        _uiState.update {
            it.copy(option = option, selectCategoryOption = option.name)
        }
    }


    fun onTransactionTypeSelected(type: TransactionType) {
        _uiState.update {
            it.copy(
                selectedType = type
            )
        }
    }

    fun onAccountSelected(account: Account) {
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
    fun onClickDate(isOpen : Boolean){
        _uiState.update {
            it.copy(dateOpen = isOpen)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateChange(newDate : Date) {
        // 1. Convert the old Date object to an Instant
        val instant = newDate.toInstant()

        // 2. Get the user's current time zone
        val zoneId = ZoneId.systemDefault()

        // 3. Create the modern ZonedDateTime object
        val zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId)

        // 4. UpdateTime the UI state with the correct ZonedDateTime object
        _uiState.update { currentState ->
            currentState.copy(dateTime = zonedDateTime) // UpdateTime the state
        }
    }

    fun onClickTime(){

    }
    fun AddTransactionUiState.toItem(): Transaction = Transaction(
        id = id,
        amount = addAmount.toDoubleOrNull() ?: 0.0,
        account = account,
        category = option,
        transactionType = selectedType,
        note = inputNote,
        description = inputDescription,
        dateTime = dateTime
    )
}
