package com.lazysloth.pocketlog.ui.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.Transaction
import com.lazysloth.pocketlog.database.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn



class DashboardScreenViewModel(
    transactionRepository: TransactionRepository,
) : ViewModel() {
        //TODO this is not required for this screen but required for details screen
//    var itemId: Int = checkNotNull(savedStateHandle[DashboardScreenDestination.itemIdArg])
    //TODO no need for that too because this screen reads only the data from repo not to modify it
//    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> =
        transactionRepository.getAllTransactions()
            .filterNotNull()
            .map {
                DashboardUiState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DashboardUiState()
            )
    companion object{
        private const val TIMEOUT_MILLIS = 5000L
    }
}

data class DashboardUiState(
    val transList : List<Transaction> = listOf()
)