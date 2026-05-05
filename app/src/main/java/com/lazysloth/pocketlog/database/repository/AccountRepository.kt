package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.data.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun saveAccount(account: Account)
    suspend fun updateAccount(account: Account)
    suspend fun deleteAccount(account: Account)
     fun getAccountByUserId(userId: String?): Flow<List<Account>>
    fun getAccountByAccountId(accountId: Long?): Flow<Account>
    fun getAccountNameByUserId(userId: String?): Flow<List<String>>
}