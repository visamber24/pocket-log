package com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.data.Account
import com.lazysloth.pocketlog.database.data.Category1
import com.lazysloth.pocketlog.database.data.Transaction
import com.lazysloth.pocketlog.database.data.TransactionType
import com.lazysloth.pocketlog.database.repository.AccountRepository
import com.lazysloth.pocketlog.database.repository.CategoryRepository
import com.lazysloth.pocketlog.database.repository.TransactionRepository
import com.lazysloth.pocketlog.di.UserPersists
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddTransactionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

class EditTransactionScreenViewmodel(
    private val transactionRepository: TransactionRepository,
    private val userPersists: UserPersists,
    accountRepository: AccountRepository,
    categoryRepository: CategoryRepository,
) : ViewModel(){
    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()
    private val itemId = MutableStateFlow<Int?>(null)
//    fun getItemId(id:Int){
//        itemId.value = id
//
//    }
    fun getItemId(id: Int) {
        itemId.value = id
        viewModelScope.launch {
            try {
                val transactionWithAccount = transactionRepository.getTransactionWithAccountByTransactionId(id)
                    .filterNotNull()
                    .first()  // Takes only the first emission and cancels the flow

                _uiState.update {current ->
                    val newState  = transactionWithAccount.transaction
                    current.copy(
                        id = newState.id,
                        addAmount = newState.amount.toString(),
                        selectedAccountId = newState.accountId,
                        selectedCategoryId = newState.categoryId,
                        selectedType = newState.transactionType,
                        inputNote = newState.note,
                        inputDescription = newState.description
                    )
                }
            } catch (e: Exception) {
                // Handle errors (e.g., no data, network issue)
                println("Exception $e occurs")
            }
        }
    }
    init {
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
                        it.copy(
                            categoryList = categories
                        )
                    }
                }
        }
    }




    // TODO : for non-flow loading transaction instead we are using it in both edit and dashboard for getting transaction
//    fun loadTransaction(id: Int) {
//        viewModelScope.launch {
//            val transaction  = transactionRepository.getTransaction(id)
//            transaction.let {
//                _uiState.value = it.toAddTransactionUiState()
//                itemId.value = id
//            }
//
//
//        }
//    }
//    @OptIn( ExperimentalCoroutinesApi::class)
//    val uiStateItem : StateFlow<AddTransactionUiState> =
//        itemId
//            .filterNotNull()
//            .flatMapLatest { id ->
//                transactionRepository.getTransaction(id)
//            }
//            .filterNotNull()
//            .map { it.toAddTransactionUiState()  }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(DashboardScreenViewModel.TIMEOUT_MILLIS),
//                initialValue = AddTransactionUiState()
//            )



    fun onAmountChange(newAmount: String) {
        _uiState.update {
            it.copy(addAmount = newAmount)
        }
    }

    fun onExpandedAccount(expanded: Boolean){
        _uiState.update {
            it.copy(
                expandedAccount = expanded
            )
        }
    }
    fun onExpandedChange(expanded: Boolean) {
        _uiState.update {
            it.copy(
                expandedCategory = expanded
            )
        }
    }

    fun onOptionSelected(option: Category1) {
        _uiState.update {
            it.copy(category = option, )
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
            it.copy(selectedAccountId = account.id)
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

    fun updateTransaction() {
        viewModelScope.launch {
            val userId = userPersists.currentId
            println("the currentUserid = ${userPersists.currentId}")
            transactionRepository.updateTransaction(_uiState.value.toItem(userId))

        }
    }

    fun deleteTransaction(){
        viewModelScope.launch {
            transactionRepository.deleteTransactionById(itemId.value)
        }
    }

    fun onClickDate(isOpen: Boolean) {
        _uiState.update {
            it.copy(dateOpen = isOpen)
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

}
fun Transaction.toAddTransactionUiState(): AddTransactionUiState {
    return AddTransactionUiState(
        id = id,
        addAmount = amount.toString(),
        selectedAccountId = accountId,
        selectedCategoryId = categoryId,
        selectedType = transactionType,
        inputNote = note,
        inputDescription = description,
        dateTime = dateTime
    )
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
