package com.lazysloth.pocketlog.ui.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.data.Account1
import com.lazysloth.pocketlog.database.repository.AccountRepository
import com.lazysloth.pocketlog.database.repository.OfflineAccountRepository
import com.lazysloth.pocketlog.di.UserPersists
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.DashboardScreenViewModel.Companion.TIMEOUT_MILLIS
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AccountScreenViewModel(
    accountRepository: AccountRepository,
    private val userPersists: UserPersists
) : ViewModel() {

    val uiStateList: StateFlow<AccountUiState> =
        accountRepository.getAccountByUserId(userPersists.currentId)
            .map{
                AccountUiState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = AccountUiState()
            )

}

data class AccountUiState(
    val accountList: List<Account1> = listOf()
)