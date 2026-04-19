package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.AccountDao
import com.lazysloth.pocketlog.database.data.Account1

class OfflineAccountRepository(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun saveAccount(account: Account1) {
        accountDao.saveAccount(account)
    }

    override suspend fun updateAccount(account: Account1) {
        accountDao.updateAccount(account)
    }

    override suspend fun deleteAccount(account: Account1) {
        accountDao.deleteAccount(account)
    }

    override suspend fun getAccountByUserId(userId: Int) {
        accountDao.getAccountByUserId(userId)
    }
}