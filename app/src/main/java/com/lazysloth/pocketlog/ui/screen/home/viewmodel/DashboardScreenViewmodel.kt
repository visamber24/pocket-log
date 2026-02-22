package com.lazysloth.pocketlog.ui.screen.home.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.Transaction
import com.lazysloth.pocketlog.database.repository.TransactionRepository
import com.lazysloth.pocketlog.ui.screen.home.TransactionDetailsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn



class DashboardScreenViewModel(
    transactionRepository: TransactionRepository,
) : ViewModel() {
        //TODO this is not required for this screen but required for details screen
//    var itemId: Int = checkNotNull(savedStateHandle[DashboardScreenDestination.itemIdArg])
private val itemId  = MutableStateFlow<Int?>(null)
    fun getItemId(id : Int){
        itemId.value = id
    }
    //TODO no need for that too because this screen reads only the data from repo not to modify it
//    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiStateList: StateFlow<DashboardUiState> =
        transactionRepository.getAllTransactions()
            .filterNotNull()
            .map {
                DashboardUiState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DashboardUiState()
            )
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStateItem : StateFlow<TransactionDetailsUiState> =
        itemId
            .filterNotNull()
            .flatMapLatest { id ->
                transactionRepository.getTransaction(id)
            }
            .filterNotNull()
            .map { it.toItemDetail()  }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TransactionDetailsUiState()
            )
    companion object{
        private const val TIMEOUT_MILLIS = 5000L
    }
    fun Transaction.toItemDetail() : TransactionDetailsUiState = TransactionDetailsUiState(
        id = id,
        amount = amount.toString(),
        account = account.name,
        category = category.name,
        transactionType = transactionType.name,
        note = note,
        description = description,
        dateTime = dateTime.toString()
    )
}

data class DashboardUiState(
    val transList : List<Transaction> = listOf()
)