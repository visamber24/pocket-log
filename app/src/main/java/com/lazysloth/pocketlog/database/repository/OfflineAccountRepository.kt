package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.AccountDao
import com.lazysloth.pocketlog.database.data.Account1
import kotlinx.coroutines.flow.Flow

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

    override fun getAccountByUserId(userId: Int): Flow<List<Account1>> {
        return accountDao.getAccountByUserId(userId)
    }

    override fun getAccountByAccountId(accountId: Long?): Flow<Account1> = accountDao.getAccountByAccountId(accountId)

    override fun getAccountNameByUserId(userId: Int): Flow<List<String>> {
        return accountDao.getAccountName(userId)
    }

}