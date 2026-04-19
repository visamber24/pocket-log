package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.data.Account
import com.lazysloth.pocketlog.database.data.Account1

interface AccountRepository {
    suspend fun saveAccount(account: Account1)
    suspend fun updateAccount(account: Account1)
    suspend fun deleteAccount(account: Account1)
    suspend fun getAccountByUserId(userId: Int)
}