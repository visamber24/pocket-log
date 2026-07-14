package com.lazysloth.pocketlog.ui.screen.other.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.data.Account
import com.lazysloth.pocketlog.data.Category1
import com.lazysloth.pocketlog.data.Transaction
import com.lazysloth.pocketlog.data.TransactionType
import com.lazysloth.pocketlog.database.repository.AccountRepository
import com.lazysloth.pocketlog.database.repository.CategoryRepository
import com.lazysloth.pocketlog.database.repository.TransactionRepository
import com.lazysloth.pocketlog.di.UserPersists
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddTransactionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

class AddTransactionScreenViewmodel(
    private val transactionRepository: TransactionRepository,
    private val userPersists: UserPersists,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val geminiModel: GeminiModel,
) : ViewModel() {

    //    val geminiState = geminiModel.extractedData.value
    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()
    var dummyCategory: Category1 = Category1()
    init {
        viewModelScope.launch {
            geminiModel.extractedData.collect { extracted ->
                val categoryList =
                    categoryRepository.getCategoryByUserId(userPersists.currentId).first()
                if (!categoryList.any { list ->
                        list.name.equals(extracted.category.name, ignoreCase = true)
                    }){
                    categoryRepository.saveCategory(Category1(name = extracted.category1,))
                }
                    _uiState.value = extracted
            }
        }
        viewModelScope.launch {
            accountRepository
                .getAccountByUserId(userPersists.currentId)
                .collect { accounts ->
                    _uiState.update {
                        it.copy(
                            accounts = accounts
                        )
                    }
                }
        }
        viewModelScope.launch {
            categoryRepository
                .getCategoryByUserId(userPersists.currentId)
                .collect { categories ->
                    _uiState.update {
                        it.copy(categoryList = categories)
                    }
                }
        }

    }

    fun generatedResponse() {
        _uiState.value = geminiModel.extractedData.value
    }
//    fun loadAccounts(){
//        viewModelScope.launch {
//            val accounts : List<String> = accountRepository.getAccountNameByUserId(userPersists.currentId)
////                .first()
//
//            _uiState.value = _uiState.value.copy(
//                accounts = accounts
//            )
//        }
//    }

    fun onAmountChange(newAmount: String) {
        _uiState.update {
            it.copy(addAmount = newAmount)
        }
    }

    fun onExpandedAccount(expanded: Boolean) {
        _uiState.update {
            it.copy(
                expandedAccount = expanded
            )
        }
    }

    fun onAccountSelected(account: Account) {
        _uiState.update {
            it.copy(
                selectedAccountId = account.id,
            )
        }
    }

    fun onExpandedCategory(expanded: Boolean) {
        _uiState.update {
            it.copy(
                expandedCategory = expanded
            )
        }
    }

    fun onCategorySelected(category: Category1) {
        _uiState.update {
            it.copy(selectedCategoryId = category.id)
        }
    }


    fun onTransactionTypeSelected(type: TransactionType) {

        _uiState.update {
            it.copy(
                selectedType = type
            )
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


    fun onClickDate(isOpen: Boolean) {
        _uiState.update {
            it.copy(dateOpen = isOpen)
        }
    }

    //    private val _isSaving = MutableStateFlow(false)
//    val isSaving: StateFlow<Boolean> = _isSaving
    private val saveMutex = Mutex()
    fun saveTransaction(pop: () -> Unit) {
        if (_uiState.value.isSaving) return
        _uiState.update {
            it.copy(
                isSaving = true
            )
        }
        pop()
        viewModelScope.launch {
            saveMutex.withLock {
                try {
                    val userId = userPersists.currentId
                    val typeOfTransaction = _uiState.value.selectedType
                    val amount = _uiState.value.addAmount.toDouble()

                    val balanceDelta = if (typeOfTransaction == TransactionType.CREDIT) {
                        amount
                    } else {
                        -amount
                    }

//            println("the currentUserid = ${userPersists.currentId}")
                    transactionRepository.insertTransaction(
                        transaction = _uiState.value.toItem(userId),
                        balanceDelta = balanceDelta
                    )
                } finally {
                    _uiState.value.isSaving = false
                }
            }
        }
    }

    fun onDateChange(newDate: Date) {
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


    fun AddTransactionUiState.toItem(userId: String?): Transaction = Transaction(
        id = id,
        userId = userId,
        amount = addAmount.toDoubleOrNull() ?: 0.0,
        accountId = selectedAccountId!!,
        categoryId = selectedCategoryId!!,
        transactionType = selectedType,
        note = inputNote,
        description = inputDescription,
        dateTime = dateTime
    )
}