package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.AccountDao
import com.lazysloth.pocketlog.database.data.Account
import kotlinx.coroutines.flow.Flow

class OfflineAccountRepository(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun saveAccount(account: Account) {
        accountDao.saveAccount(account)
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(account)
    }

    override suspend fun deleteAccount(account: Account) {
        accountDao.deleteAccount(account)
    }

    override fun getAccountByUserId(userId: Int): Flow<List<Account>> {
        return accountDao.getAccountByUserId(userId)
    }

    override fun getAccountByAccountId(accountId: Long?): Flow<Account> = accountDao.getAccountByAccountId(accountId)

    override fun getAccountNameByUserId(userId: Int): Flow<List<String>> {
        return accountDao.getAccountName(userId)
    }

}