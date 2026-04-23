package com.lazysloth.pocketlog.ui.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.data.Transaction
import com.lazysloth.pocketlog.database.data.TransactionWithAccount
import com.lazysloth.pocketlog.database.repository.AccountRepository
import com.lazysloth.pocketlog.database.repository.TransactionRepository
import com.lazysloth.pocketlog.di.UserPersists
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.TransactionDetailsUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class DashboardScreenViewModel(
    private val transactionRepository: TransactionRepository,
    userPersists: UserPersists,
    private val accountRepository: AccountRepository

) : ViewModel() {
    //TODO this is not required for this screen but required for details screen
//    var itemId: Int = checkNotNull(savedStateHandle[DashboardScreenDestination.itemIdArg])
    private val itemId = MutableStateFlow<Int?>(null)
    fun getItemId(id: Int) {
        itemId.value = id
    }

    //TODO no need for that too because this screen reads only the data from repo not to modify it
//    private val _uiState = MutableStateFlow(DashboardUiState())
    var userId = MutableStateFlow<Int?>(null)

    val transactionList: StateFlow<DashboardUiState> =
        transactionRepository.getTransactionWithAccount(userPersists.currentId)
            .map {list ->
                DashboardUiState(
                    transList = list,
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = DashboardUiState()
            )

//    val accountList: StateFlow<AddTransactionUiState> =
//        accountRepository.getAccountNameByUserId(userPersists.currentId)
//            .map {
//                AddTransactionUiState(accounts = it)
//            }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000),
//                initialValue = AddTransactionUiState()
//            )


//    @OptIn(ExperimentalCoroutinesApi::class)
//    val uiStateList: StateFlow<DashboardUiState> =
//        transactionRepository.getAllTransactions(userPersists.currentId)
//            .map {
//                DashboardUiState(it)
//            }.stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = DashboardUiState()
//            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStateItem: StateFlow<TransactionDetailsUiState> =
        itemId
            .filterNotNull()
            .flatMapLatest { id ->
                transactionRepository.getTransactionByTransactionId(id)
            }
            .filterNotNull()
            .map { it.toItemDetail() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TransactionDetailsUiState()
            )

    companion object {
        const val TIMEOUT_MILLIS = 5000L
    }

}

fun Transaction.toItemDetail(): TransactionDetailsUiState = TransactionDetailsUiState(
    id = id,
    amount = amount.toString(),
    account = accountId.toString(),
    category = category.name,
    transactionType = transactionType.name,
    note = note,
    description = description,
    dateTime = dateTime.toString()
)

data class DashboardUiState(
    val transList: List<TransactionWithAccount> = listOf(),

    )