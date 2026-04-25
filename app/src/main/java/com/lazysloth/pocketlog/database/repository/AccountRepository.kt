package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.data.Account
import com.lazysloth.pocketlog.database.data.Account1
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun saveAccount(account: Account1)
    suspend fun updateAccount(account: Account1)
    suspend fun deleteAccount(account: Account1)
     fun getAccountByUserId(userId: Int): Flow<List<Account1>>
    fun getAccountByAccountId(accountId: Long?): Flow<Account1>
    fun getAccountNameByUserId(userId: Int): Flow<List<String>>
}